package dev.ancaghenade.shipmentpicturelambdavalidator;

import java.io.IOException;
import java.net.URI;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

public class S3ClientHelper {

  private static final String ENVIRONMENT = System.getenv("ENVIRONMENT");
  private static PropertiesProvider properties = new PropertiesProvider();

  public static S3Client getS3Client() throws IOException {

    if (properties.getProperty("environment.dev").equals(ENVIRONMENT)) {

      return S3Client.builder()
          .region(Region.of(properties.getProperty("s3.region")))
          .credentialsProvider(StaticCredentialsProvider.create(
              AwsBasicCredentials.create(properties.getProperty("credentials.access-key"),
                  properties.getProperty("credentials.secret-key"))))
          .endpointOverride(URI.create(properties.getProperty("s3.endpoint")))
          .forcePathStyle(true)
          .build();
    } else {
      System.out.println("AWS S3 client is used");

      return S3Client.builder().build();
    }
  }

}
