#!/usr/bin/env bash

mvn clean package
scp -i ~/Downloads/key.pem ~/workspace/Symantec_VM/target/Symantec_VM-3.1.0-fat.jar  ubuntu@ec2-54-174-126-248.compute-1.amazonaws.com:
