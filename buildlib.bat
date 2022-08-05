javac -cp .\src -cp C:\blp\BloombergWindowsSDK7Aug2020\JavaAPI\v3.15.0.1\lib\blpapi3.jar .\src\com\bloomberg\samples\easymsx\*.java -d bin
javadoc -cp C:\blp\BloombergWindowsSDK7Aug2020\JavaAPI\v3.15.0.1\lib\blpapi3.jar  .\src\com\bloomberg\samples\easymsx\*.java -d docs
jar cvfm easymsx.jar .\resources\manifest.txt -C bin .
