package com.demo;

import com.azure.core.credential.AccessToken;
import com.azure.core.credential.TokenRequestContext;
import com.azure.cosmos.*;
import com.azure.core.credential.TokenCredential;
import reactor.core.publisher.Mono;
import com.azure.identity.DefaultAzureCredentialBuilder;
import com.azure.identity.ClientSecretCredentialBuilder;
import java.util.List;


import java.time.Duration;



public class CosmosClientDemo {



    public void execute1() {

        authenticate();

    }


    private static void authenticate() {

        int maxPoolSize =100 ;
        int maxRetryAttempts = 10;
        int retryWaitTimeInSeconds = 1;

        String hostName = "https://medium-blog.documents.azure.com:443/";
        String masterKey= "secret";
        //String databaseName= "AzureSampleFamilyDatabase";
        ConsistencyLevel consistencyLevel = ConsistencyLevel.SESSION;
        CosmosClient syncClient = null;


        System.out.println("Payload size : " + 500);
        syncClient = buildCosmosClientToken(ConnectionMode.DIRECT, maxPoolSize, maxRetryAttempts, retryWaitTimeInSeconds, hostName, masterKey, consistencyLevel);
        //syncClient = buildCosmosClient(ConnectionMode.DIRECT, maxPoolSize, maxRetryAttempts, retryWaitTimeInSeconds, hostName, masterKey, consistencyLevel);
        System.out.println(syncClient);

    }




    private static ThrottlingRetryOptions getRetryOptions(int maxRetryAttempts,
                                                          int retryWaitTimeInSeconds) {
        ThrottlingRetryOptions retryOptions = new ThrottlingRetryOptions();
        retryOptions.setMaxRetryAttemptsOnThrottledRequests(maxRetryAttempts);
        retryOptions.setMaxRetryWaitTime(Duration.ofSeconds(retryWaitTimeInSeconds));

        return retryOptions;
    }


    private static CosmosClient buildCosmosClient(ConnectionMode connectionMode, int maxPoolSize, int maxRetryAttempts,
                                                  int retryWaitTimeInSeconds, String hostName, String masterKey, ConsistencyLevel consistencyLevel ) {

        ThrottlingRetryOptions retryOptions = getRetryOptions(maxRetryAttempts, retryWaitTimeInSeconds);

        return new CosmosClientBuilder()
                .endpoint(hostName)
                .key(masterKey)
                .directMode(DirectConnectionConfig.getDefaultConfig().setIdleConnectionTimeout(Duration.ofMinutes(15)))
                .throttlingRetryOptions(retryOptions)
                .consistencyLevel( consistencyLevel )
                .buildClient();

    }

    private static CosmosClient buildCosmosClientToken(ConnectionMode connectionMode, int maxPoolSize, int maxRetryAttempts,
        int retryWaitTimeInSeconds, String hostName, String masterKey, ConsistencyLevel consistencyLevel ) {

            ThrottlingRetryOptions retryOptions = getRetryOptions(maxRetryAttempts, retryWaitTimeInSeconds);

            TokenCredential ServicePrincipal = new ClientSecretCredentialBuilder()
                .tenantId("8f12c261-6dbf-47c3-918f-1d15198a3b3b")
                .clientId("254a124e-9cb5-49b2-919d-faf8c141ac0a")
                .clientSecret("secret")
                .build();


        return new CosmosClientBuilder()
                    .endpoint(hostName)
                    .credential(ServicePrincipal)
                    .directMode(DirectConnectionConfig.getDefaultConfig().setIdleConnectionTimeout(Duration.ofMinutes(15)))
                    .throttlingRetryOptions(retryOptions)
                    .consistencyLevel( consistencyLevel )
                    .buildClient();

        }

    public static void main(String[] args)
    {
        System.out.println("I am a Geek");
        CosmosClientDemo C1 = new CosmosClientDemo();
        C1.execute1();
    }

}
