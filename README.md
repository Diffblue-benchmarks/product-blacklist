# Product Blacklist

Allows to manage a blacklist for a customer.

## Requirements

* Java 8+
* Maven
* AWS Client (optional, if you want to manage infrastructure)

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

## Load tests

The load tests are based on Gatling with Taurus templates. Taurus allows to create declarative tests in yaml files without the need to write any code, but allows to write code for more complex scenarios.

### Run Tests

#### Local

##### Installation

brew install bzt

You're done! Bear in mind it may fail with a message saying to try again. If you do, it will work. For some reason...

##### Execute

```bzt <config_file>.yml```

where

**config_file** is the file you actually want to execute. You can do `*.yaml` to run everything.

To get a fancy report on your browser, run 

```bzt -report <config_file>.yml```

Known issues:
* Gatling doesn't seem to run with Java 11. It works well with Java 8. Haven't tested remaining versions.
https://groups.google.com/forum/#!topic/codename-taurus/hEGr5-sDwpQ

#### Docker

##### Installation

1) As expected, install docker locally.

2) ```docker pull blazemeter/taurus```

##### Execute

```sudo docker run -it -v <test_folder>:/bzt-configs blazemeter/taurus <config_file>.yml```

where

**test_folder** is the folder where your yaml files are and that you want to mount as a volume in the docker container.
**config_file** is the file you actually want to execute. You can do `*.yaml` to run everything.

Known issues:
* I came across a bug running it in mac and reading online I found a page saying there was a known mac bug. Can't find the source now, though. Give it a go, if it works for you, great.