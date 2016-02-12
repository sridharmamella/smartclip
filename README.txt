cd /usr/local/spark

# execute the the following command which should write the "Pi is roughly 3.1418" into the logs
./bin/spark-submit --class com.smartclip.app.SmartClipApp --master yarn-cluster --driver-memory 1g --executor-memory 1g --executor-cores 1 smartclip_1.0.jar