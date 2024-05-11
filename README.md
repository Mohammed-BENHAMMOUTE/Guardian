#Guardian
## File Encryption and Decryption Utility

This utility is a command-line application written in Java that allows you to encrypt and decrypt files using AES encryption. It uses the Apache Commons CLI library to parse command-line arguments and Maven for dependency management.

## Prerequisites

Before you begin, ensure you have met the following requirements:

- You have installed a Java JDK 1.8 or higher.
- You have installed Maven.

## Using the Utility

To use the utility, follow these steps:

1. Clone the repository: `git clone https://github.com/yourusername/your-repo-name.git`
2. Navigate to the project directory: `cd your-repo-name`
3. Compile the project: `mvn compile`

### Encrypting a File

To encrypt a file, use the following command:

```bash
mvn exec:java -Dexec.mainClass="Main" -Dexec.args="-o encrypt -f /path/to/file -p password"
```
This command will create a new file with the same name as the original file, but with ".encrypted" appended before the file extension. The original file will not be modified.
For example here I will encrypt a file called text.cpp:
![image](https://github.com/Mohammed-BENHAMMOUTE/Guardian/assets/145193365/4e9de5ae-3db8-4fc8-a0e9-78862bb4febe)
a new file is created wich contains the encrypted file:
![image](https://github.com/Mohammed-BENHAMMOUTE/Guardian/assets/145193365/2c1111de-0f7f-4775-a281-3b74c8184dd2)

##Decrypting a file
To decrypt a file, use the following command:
```bash
mvn exec:java -Dexec.mainClass="Main" -Dexec.args="-o decrypt -f /path/to/file -p password"
```
for example here I'm decrupting the `textencrupted.cpp` which is the file we gor earlier when we incrypted the text.cpp:
![image](https://github.com/Mohammed-BENHAMMOUTE/Guardian/assets/145193365/ac629739-e3eb-465a-9147-aa14c7618cbb)

##The utility consists of three main classes:  

`Main`: This is the entry point of the application. It uses the Apache Commons CLI library to parse command-line arguments and calls the appropriate method in the FolderLocker or FolderUnlocker class based on the operation specified by the user.

`FolderLocker`: This class contains the method for encrypting a file. It uses the AES encryption algorithm in CBC mode with PKCS5 padding. The encryption key is derived from the password specified by the user using the SHA-1 hash function.

`FolderUnlocker`: This class contains the method for decrypting a file. It uses the same AES encryption algorithm and key derivation method as the FolderLocker class.

