package com.cde.pcfsample.movie.Util;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.AnonymousAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import io.findify.s3mock.S3Mock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.SocketUtils;

@Configuration
public class AwsS3Config {
    @Bean

    public AmazonS3 s3Client() {

        int port = SocketUtils.findAvailableTcpPort();

        S3Mock api = new S3Mock.Builder().withPort(port).withInMemoryBackend().build();

        api.start(); // Start the Mock S3 server locally on available port



        AmazonS3 amazonS3 = AmazonS3ClientBuilder.standard()

                .withPathStyleAccessEnabled(true)

                .withCredentials(new AWSStaticCredentialsProvider(new AnonymousAWSCredentials())) //use anonymous credentials.

                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://localhost:" + port, "us-west-2"))

                .build();

        amazonS3.createBucket("test");

        return amazonS3;

    }
}
