public class Encryptor {
    private final String ABC = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /** A two-dimensional array of single-character strings, instantiated in the constructor */
    private String[][] letterBlock;

    /** The number of rows of letterBlock, set by the constructor */
    private int numRows;

    /** The number of columns of letterBlock, set by the constructor */
    private int numCols;
    private int offset;

    /** Constructor*/
    public Encryptor(int r, int c, int offset) {
        letterBlock = new String[r][c];
        numRows = r;
        numCols = c;
        this.offset = offset;
    }

    public String[][] getLetterBlock() {
        return letterBlock;
    }

    /** Places a string into letterBlock in row-major order.
     *
     *   @param str  the string to be processed
     *
     *   Postcondition:
     *     if str.length() < numRows * numCols, "A" in each unfilled cell
     *     if str.length() > numRows * numCols, trailing characters are ignored
     */
    public void fillBlock(String str) {
        int index = 0;
        for (int i = 0; i < letterBlock.length; i++) {
            for (int j = 0; j < letterBlock[0].length; j++) {
                if (index >= str.length()) {
                    letterBlock[i][j] = "A";
                } else {
                    letterBlock[i][j] = str.substring(index, index + 1);
                }
                index++;
            }
        }
    }

    /** Extracts encrypted string from letterBlock in column-major order.
     *
     *   Precondition: letterBlock has been filled
     *
     *   @return the encrypted string from letterBlock
     */
    public String encryptBlock() {
        String encrypted = "";
        for (int i =0; i<letterBlock[0].length; i++){
            for (int j=0; j<letterBlock.length;j++){
                encrypted+= letterBlock[j][i];
            }
        }
        return encrypted;
    }

    /** Encrypts a message.
     *
     *  @param message the string to be encrypted
     *
     *  @return the encrypted message; if message is the empty string, returns the empty string
     */
    public String encryptMessage(String message) {
        String finalString = "";
        double numOfBlocks =(double) message.length()/(double)(numRows*numCols);
        if (numOfBlocks%1!=0){
            numOfBlocks++;
        }
        int num = (int)numOfBlocks;
        int count =0;
        for (int i = 0; i<num-1; i++){
            fillBlock(message.substring((count*numRows*numCols), ((count+1)*numRows*numCols)));
            finalString+=encryptBlock();
            count++;
        }
        fillBlock(message.substring(count*numRows*numCols));
        finalString+=encryptBlock();

        return finalString;
    }

    /**  Decrypts an encrypted message. All filler 'A's that may have been
     *   added during encryption will be removed, so this assumes that the
     *   original message (BEFORE it was encrypted) did NOT end in a capital A!
     *
     *   NOTE! When you are decrypting an encrypted message,
     *         be sure that you have initialized your Encryptor object
     *         with the same row/column used to encrypted the message! (i.e.
     *         the “encryption key” that is necessary for successful decryption)
     *         This is outlined in the precondition below.
     *
     *   Precondition: the Encryptor object being used for decryption has been
     *                 initialized with the same number of rows and columns
     *                 as was used for the Encryptor object used for encryption.
     *
     *   @param encryptedMessage  the encrypted message to decrypt
     *
     *   @return  the decrypted, original message (which had been encrypted)
     *
     *   TIP: You are encouraged to create other helper methods as you see fit
     *        (e.g. a method to decrypt each section of the decrypted message,
     *         similar to how encryptBlock was used)
     */
    public String decryptMessage(String encryptedMessage) {
        String finalString = "";
        double numOfBlocks =(double) encryptedMessage.length()/(double)(numRows*numCols);
        if (numOfBlocks%1!=0){
            numOfBlocks++;
        }
        int num = (int)numOfBlocks;
        int count = 0;
        for (int i = 0; i<num-1; i++){
            fillBlock2(encryptedMessage.substring((count*numRows*numCols), ((count+1)*numRows*numCols)));
            // finalString+=encryptBlock();

            for (int j = 0; j < letterBlock.length; j++) {
                for (int x = 0; x < letterBlock[0].length; x++) {
                    finalString += letterBlock[j][x];
                }
            }
            count++;
        }

        fillBlock2(encryptedMessage.substring(count*numRows*numCols));
        for (int j = 0; j < letterBlock.length; j++) {
            for (int x = 0; x < letterBlock[0].length; x++) {
                finalString += letterBlock[j][x];
            }
        }

        String letter = finalString.substring(finalString.length() - 1);

        while (letter.equals("A")) {
            finalString= finalString.substring(0, finalString.length()-1);

            letter = finalString.substring(finalString.length() - 1);
        }

        return finalString;
    }

    private void fillBlock2(String str) {
        int index = 0;
        for (int i = 0; i < letterBlock[0].length; i++) {
            for (int j = 0; j < letterBlock.length; j++) {
                if (index >= str.length()) {
                    letterBlock[j][i] = "A";
                } else {
                    letterBlock[j][i] = str.substring(index, index + 1);
                }
                index++;
            }
        }
    }

    public String superEncryptMessage(String encryptMessage) {
        String message = "";

        //offsetting the message using the variable "offset" set in the parameter in the constructor
        //ABC is a final String variable

        for (int j = 0; j < encryptMessage.length(); j++) {
            int index = ABC.indexOf(encryptMessage.substring(j , j + 1));
            int offsetIndex = index + offset;

            if (index == -1) { //if there are spaces in "message" then it would be found in ABC
                message += encryptMessage.substring(j, j+1);
            } else if (index + offset >= ABC.length()){
                int tempInd = index;
                int index2 = 0;
                String add = "";
                for (int x = 0; x < offset; x++) {
                    if (tempInd != ABC.length()-1) {
                        add = ABC.substring(tempInd, tempInd+1);
                        tempInd++;
                    } else {
                        add = ABC.substring(index2, index2 + 1);
                        index2++;
                    }
                }
                message += add;
            } else {
                message += ABC.substring(offsetIndex, offsetIndex + 1);
            }
        }


        System.out.println(message);

        String finalMessage = "";
        double numOfBlocks = (double) message.length()/(double)(numRows*numCols);
        if (numOfBlocks%1 != 0){
            numOfBlocks++;
        }
        int num = (int)numOfBlocks;
        int count =0;
        for (int i = 0; i < num-1; i++){
            fillBlock(message.substring((count*numRows*numCols), ((count+1)*numRows*numCols)));
            finalMessage += encryptBlock();
            count++;
        }
        fillBlock(message.substring(count*numRows*numCols));
        finalMessage += encryptBlock();

        return finalMessage;
    }

    public String superDecryptMessage(String encryptedMessage) {
        String finalString = "";
        double numOfBlocks = (double)encryptedMessage.length()/(double)(numRows*numCols);
        if (numOfBlocks%1 != 0){
            numOfBlocks++;
        }
        int num = (int)numOfBlocks;
        int count = 0;
        for (int i = 0; i < num-1; i++) {
            fillBlock2(encryptedMessage.substring((count*numRows*numCols), ((count+1)*numRows*numCols)));

            for (int j = 0; j < letterBlock.length; j++) {
                for (int x = 0; x < letterBlock[0].length; x++) {
                    finalString += letterBlock[j][x];
                }
            }
            count++;
        }

        fillBlock2(encryptedMessage.substring(count*numRows*numCols));
        for (int j = 0; j < letterBlock.length; j++) {
            for (int x = 0; x < letterBlock[0].length; x++) {
                finalString += letterBlock[j][x];
            }
        }

        String letter = finalString.substring(finalString.length() - 1);

        while (letter.equals("A")) {
            finalString= finalString.substring(0, finalString.length()-1);

            letter = finalString.substring(finalString.length() - 1);
        }

        String message = "";

        for (int j = 0; j < finalString.length(); j++) {
            int index = ABC.indexOf(finalString.substring(j , j + 1));
            int offsetIndex = index - offset;

            if (index == -1) { //if there are spaces in "message" then it would be found in ABC
                message += finalString.substring(j, j+1);
            } else if (index - offset < 0){
                int tempInd = index;
                int index2 = ABC.length() - 1;
                String add = "";
                for (int x = 0; x < offset; x++) {
                    if (tempInd != 0) {
                        add = ABC.substring(tempInd, tempInd + 1);
                        tempInd--;
                    } else {
                        add = ABC.substring(index2, index2 + 1);
                        index2--;
                    }
                }
                message += add;
            } else {
                message += ABC.substring(offsetIndex, offsetIndex + 1);
            }
        }

        finalString = message;

        return finalString;
    }
}
