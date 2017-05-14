# jmxMapSerialization
encapsulate a Map to provide a useful visualization in jConsole

Not much to say there: 

JMX's default serialization of a Map is not really useful, as it just exposes all its methods. 

I spent some time figuring out how to get a Map's data to be deserialized as (pageable) matrix data - this is why I wanted to share my findings. 

