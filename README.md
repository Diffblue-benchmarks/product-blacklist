# Product Blacklist

Allows to manage a blacklist for a customer.

## Requirements

* Java 8+
* Maven
* AWS Client (optional, if you want to manage infrastructure)
* Terraform (optional, if you want to manage infrastructure)

## Setup

### A) Set up AWS Keys.

1) Generate your aws keys according to https://docs.aws.amazon.com/IAM/latest/UserGuide/id_credentials_access-keys.html, using whatever methods you prefer.

2) Install the aws cli (search for your OS): https://docs.aws.amazon.com/cli/latest/userguide/install-macos.html.

3) Run ```aws configure``` and follow the instructions to add the generated keys. The files ```<home>/.aws/credentials``` and ```<home>/.aws/config``` will be created as result.

As an alternative, you can simply create the files ```<home>/.aws/credentials``` and ```<home>/.aws/config``` with the credentials without the need to install aws client.

PS: If using docker, you need to set the environment variables AWS_ACCESS_KEY_ID and AWS_SECRET_ACCESS_KEY.

### B) Install maven.

As per instructions: https://maven.apache.org/install.html.

### C) Install terraform.

As per instructions: https://learn.hashicorp.com/terraform/getting-started/install.

## Build

On this folder, execute ```mvn clean package```. The first time you execute this, it will download the dependencies.

PS: You won't be able to reach Maven Central from ```Blackfriars wi-fi``` network. Switch to ```O2 Wifi``` when running maven commands.