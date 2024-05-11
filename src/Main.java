import org.apache.commons.cli.*;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        Options options = new Options();

        Option operationOpt = new Option("o", "operation", true, "operation to perform (encrypt or decrypt)");
        operationOpt.setRequired(true);
        options.addOption(operationOpt);

        Option fileOpt = new Option("f", "file", true, "file to encrypt or decrypt");
        fileOpt.setRequired(true);
        options.addOption(fileOpt);

        Option passwordOpt = new Option("p", "password", true, "password for encryption or decryption");
        passwordOpt.setRequired(true);
        options.addOption(passwordOpt);

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd;
        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("utility-name", options);
            System.exit(1);
            return;
        }

        String operation = cmd.getOptionValue("operation");
        File file = new File(cmd.getOptionValue("file"));
        String password = cmd.getOptionValue("password");

        try {
            if ("encrypt".equals(operation)) {
                FolderLocker.encryptFile(file, password);
            } else if ("decrypt".equals(operation)) {
                FolderUnlocker.decryptFile(file, password);
            } else {
                System.out.println("Invalid operation. Use 'encrypt' or 'decrypt'.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}