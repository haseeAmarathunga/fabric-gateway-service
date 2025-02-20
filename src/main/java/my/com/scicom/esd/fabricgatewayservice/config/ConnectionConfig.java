package my.com.scicom.esd.fabricgatewayservice.config;

import io.grpc.Grpc;
import io.grpc.ManagedChannel;
import io.grpc.TlsChannelCredentials;
import org.hyperledger.fabric.client.*;
import org.hyperledger.fabric.client.identity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.InvalidKeyException;
import java.security.cert.CertificateException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Configuration
@PropertySources({ @PropertySource("classpath:fabric-gateway-application.properties"), @PropertySource(value = "file:${FABRIC_CONFIG_PATH}/fabric-gateway-application.properties", ignoreResourceNotFound = true) })
public class ConnectionConfig
{
	@Autowired
	private Environment environment;

	public Contract contract;

	private static String mspID = "";
	private static Path certDirPath = null;
	private static Path keyDirPath = null;
	private static Path tlsCertPath = null;
	private static String peerEndpoint = "";
	private static String overrideAuth = "";

	@Bean
	public Contract createConnection() throws IOException, CertificateException, InvalidKeyException, InterruptedException
	{
		String channelName = System.getenv().getOrDefault( "CHANNEL_NAME", environment.getProperty( "CHANNEL_NAME" ) );
		String chainCodeName = System.getenv().getOrDefault( "CHAINCODE_NAME", environment.getProperty( "CHAINCODE_NAME" ) );
		Path cryptoPath = Path.of( Objects.requireNonNull( environment.getProperty( "CRYPTO_PATH" ) ) );

		mspID = System.getenv().getOrDefault( "MSP_ID", environment.getProperty( "MSP_ID" ) );
		certDirPath = cryptoPath.resolve( Path.of( Objects.requireNonNull( environment.getProperty( "CERT_DIR_PATH" ) ) ) );
		keyDirPath = cryptoPath.resolve( Path.of( Objects.requireNonNull( environment.getProperty( "KEY_DIR_PATH" ) ) ) );
		tlsCertPath = cryptoPath.resolve( Path.of( Objects.requireNonNull( environment.getProperty( "TLS_CERT_PATH" ) ) ) );
		peerEndpoint = environment.getProperty( "PEER_ENDPOINT" );
		overrideAuth = environment.getProperty( "OVERRIDE_AUTH" );

		var channel = newGrpcConnection();
		var builder = Gateway.newInstance().identity( newIdentity() ).signer( newSigner() ).hash( Hash.SHA256 ).connection( channel )
				// Default timeouts for different gRPC calls
				.evaluateOptions( options -> options.withDeadlineAfter( 5, TimeUnit.SECONDS ) ).endorseOptions( options -> options.withDeadlineAfter( 15, TimeUnit.SECONDS ) ).submitOptions( options -> options.withDeadlineAfter( 5, TimeUnit.SECONDS ) )
				.commitStatusOptions( options -> options.withDeadlineAfter( 1, TimeUnit.MINUTES ) );

		try (var gateway = builder.connect())
		{
			var network = gateway.getNetwork( channelName );
			contract = network.getContract( chainCodeName );
			initLedger();
			return contract;
		}
		catch ( EndorseException | CommitException | SubmitException | CommitStatusException e )
		{
			throw new RuntimeException( e );
		}
//		finally
//		{
//			// channel.shutdownNow().awaitTermination( 5, TimeUnit.SECONDS );
//		}
	}

	public static ManagedChannel newGrpcConnection() throws IOException
	{
		var credentials = TlsChannelCredentials.newBuilder().trustManager( tlsCertPath.toFile() ).build();
		return Grpc.newChannelBuilder( peerEndpoint, credentials ).overrideAuthority( overrideAuth ).build();
	}

	public static Identity newIdentity() throws IOException, CertificateException
	{
		try (var certReader = Files.newBufferedReader( getFirstFilePath( certDirPath ) ))
		{
			var certificate = Identities.readX509Certificate( certReader );
			return new X509Identity( mspID, certificate );
		}
	}

	public static Signer newSigner() throws IOException, InvalidKeyException
	{
		try (var keyReader = Files.newBufferedReader( getFirstFilePath( keyDirPath ) ))
		{
			var privateKey = Identities.readPrivateKey( keyReader );
			return Signers.newPrivateKeySigner( privateKey );
		}
	}

	private static Path getFirstFilePath( Path dirPath ) throws IOException
	{
		try (var keyFiles = Files.list( dirPath ))
		{
			return keyFiles.findFirst().orElseThrow();
		}
	}

	private void initLedger() throws EndorseException, SubmitException, CommitStatusException, CommitException
	{
		System.out.println( "\n--> Submit Transaction: InitLedger, function creates the initial set of passports on the ledger" );
		contract.submitTransaction( "InitLedger" );
		System.out.println( "*** Transaction committed successfully" );
	}
}
