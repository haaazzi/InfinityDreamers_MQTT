구성

MqttIn -> Json -> DeviceInfo -> Topic -> Sensor -> MqttOut

- MqttIn : Mqtt 브로커로 부터 메시지를 받고 Command Line Arguments로 받은 Topic을 설정해서 Message처리

- Json : MqttIn에서 받은 String형식의 Message를 Json형식으로 변환

- DeviceInfo : Message의 Json형식 데이터중 DeviceInfo 추출해 새로운 Json객체 생성

- Topic : DeviceInfo로 Topic생성

- Sensor : Topic에 Sensor별 데이터로 새로운 Json객체 생성 

- MqttOut : 가공된 Message Out(Publish)

