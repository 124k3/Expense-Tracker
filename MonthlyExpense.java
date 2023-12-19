
/**
 * Author: 124k3 (izake)
 * Date: 17December2023 - 18December2023
 * About this program: It uses for now the basic java functionality to read and write data from the user and inputs only 1 entry for now in the database(.csv file).
 * requirements: JVM
  
 */

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.Scanner;

class ItemProperties {

    int eventDate, eventDeadline;
    float itemPrice;
    String modeOfPayment;
    String itemName, ddmmyyyy, expenseType;
    boolean installments;

    // constructors
    ItemProperties() {
    }

    // if the personal/business/non-profit transaction is successfull
    ItemProperties(
            String itemName,
            float itemPrice,
            int eventDate,
            String expenseType,
            String modeOfPayment,
            String ddmmyyyy) {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.eventDate = eventDate;
        this.modeOfPayment = modeOfPayment;
        this.expenseType = expenseType;
        this.ddmmyyyy = ddmmyyyy;
    }

    // if the business/personal/non-profit transaction is in debt
    ItemProperties(
            String itemName,
            float itemPrice,
            boolean installments,
            String modeOfPayment,
            int eventDate,
            int eventDeadline,
            String expenseType,
            String ddmmyyyy) {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.installments = installments;
        this.modeOfPayment = modeOfPayment;
        this.eventDate = eventDate;
        this.eventDeadline = eventDeadline;
        this.expenseType = expenseType;
        this.ddmmyyyy = ddmmyyyy;
    }
}

class FileHandler {
    void csvTitle(FileWriter fileWriter, int what2do) throws IOException {
        String[] properties;
        if (what2do == 0) {
            properties = new String[] {
                    "Item Name",
                    "Item Price",
                    "Event Date",
                    "Mode of Payment",
                    "Expense Type",
                    "Entry Date"
            };
            for (int i = 0; i < properties.length; i++) {
                fileWriter.write(properties[i]);
                if (i < properties.length - 1) {
                    fileWriter.write(",");
                }
            }

        }
        if (what2do == 1) {
            properties = new String[] {
                    "Item Name",
                    "Item Price",
                    "Installments",
                    "Mode of Payment",
                    "Event Date",
                    "Event Deadline",
                    "Expense Type",
                    "Entry Date"
            };
            for (int i = 0; i < properties.length; i++) {
                fileWriter.write(properties[i]);
                if (i < properties.length - 1) {
                    fileWriter.write(",");
                }

            }
        }
        fileWriter.write("\n");
    }

    void save2file(ItemProperties item, String fileName, int what2do) {
        try {
            File file = new File(fileName);
            boolean fileExists = file.exists();

            FileWriter fileWriter = new FileWriter(fileName, true);

            // If the file doesn't exist, write the header
            if (!fileExists) {
                csvTitle(fileWriter, what2do);
            }

            fileWriter.write(item.itemName + ",");
            fileWriter.write(item.itemPrice + ",");
            if (what2do == 0) {
                fileWriter.write(item.eventDate + ",");

            } else if (what2do == 1) {
                fileWriter.write(item.installments + ",");
            }
            fileWriter.write(item.modeOfPayment + ",");
            if (what2do == 1) {

                fileWriter.write(item.eventDate + ",");
                fileWriter.write(item.eventDeadline + ",");
            }
            fileWriter.write(item.expenseType + ","); // Include expense type for monthly expense
            fileWriter.write(item.ddmmyyyy + ",");
            // Ensure to add a newline character only once
            fileWriter.write("\n");

            fileWriter.close();
            System.out.println("Data written to file successfully");
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file...");
        }
    }

    void append2file(ItemProperties item, String fileName, int what2do) {
        try {
            FileWriter fileWriter = new FileWriter(fileName, true); // (true) parameter , indicates append mode

            if (what2do == 0) {
                fileWriter.append(item.itemName + ",");
                fileWriter.append(item.itemPrice + ",");
                fileWriter.append(item.eventDate + ",");
                fileWriter.append(item.modeOfPayment + ",");
            } else if (what2do == 1) {
                fileWriter.append(item.itemName + ",");
                fileWriter.append(item.itemPrice + ",");
                fileWriter.append(item.installments + ",");
                fileWriter.append(item.modeOfPayment + ",");
                fileWriter.append(item.eventDate + ",");
                fileWriter.append(item.eventDeadline + ",");
            }

            fileWriter.append(item.expenseType + ",");
            fileWriter.append(item.ddmmyyyy + ",");
            fileWriter.append("\n");

            fileWriter.close();
            System.out.println("\tData written to file successfully");
        } catch (IOException e) {
            System.out.println("An error occurred while appending the data to the : " + fileName);
        }
    }


}

class MonthlyExpense {
    static int what2do;

    static Scanner scnr = new Scanner(System.in);

    String getStringData(String prompt) {
        try {
            System.out.print(prompt);
            return scnr.nextLine();
        } catch (Exception e) {
            scnr.next();
            return getStringData(prompt);
        }
    }

    int getIntData(String prompt) {
        try {
            System.out.print(prompt);
            return scnr.nextInt();
        } catch (Exception e) {
            scnr.next();
            return getIntData(prompt);
        }
    }

    float getFloatData(String prompt) {
        try {
            System.out.print(prompt);
            return scnr.nextFloat();
        } catch (Exception e) {
            scnr.next();
            return getIntData(prompt);
        }
    }

    boolean getBoolData(String prompt) {
        try {
            System.out.print(prompt);
            return scnr.nextBoolean();
        } catch (Exception e) {
            scnr.next();
            return getBoolData(prompt);
        }
    }

    public static void main(String args[]) {
        boolean restartCondition = false;
        MonthlyExpense monthlyexpense = new MonthlyExpense();
        // asks the input and takes data to the variables
        // ----------------------------------------------------------------
        what2do = monthlyexpense.getIntData("Enter 0 for the Monthly-Expense and 1 for the Debt-Ledger: ");

        // if the payment was successful
        if (what2do == 0) {
            scnr.nextLine();
            do {
                String itemName = monthlyexpense.getStringData(
                        "Enter the Name of the Product: ");
                int itemPrice = monthlyexpense.getIntData(
                        "Enter the Price of the Product: ");
                scnr.nextLine();
                int eventDate = monthlyexpense.getIntData(
                        "Enter the Date when the payment was made(DD/MM/YY): ");
                scnr.nextLine();

                int value = monthlyexpense.getIntData(
                        "Enter the expense(0- personal, 1-business, 2-nonProfit)");
                scnr.nextLine();
                String[] typeOfExpense = {
                        "Personal",
                        "Business",
                        "Non-profit"
                };
                String expenseType = typeOfExpense[value];

                int value2 = monthlyexpense.getIntData(
                        "Enter the mode of payment(0-Cash, 1-NEtBanking, 2-Card): ");
                scnr.nextLine();
                String[] PaymentMode = {
                        "Cash",
                        "NetBanking",
                        "Card"
                };

                String modeOfPayment = PaymentMode[value2];
                Date date = new Date();
                String dateMonthYear = date.toString();
                String[] SplittedDate = dateMonthYear.split(" ");
                dateMonthYear = SplittedDate[0] +
                        SplittedDate[1] +
                        SplittedDate[2] +
                        " " +
                        SplittedDate[5];

                // creates items using constructor1
                // ----------------------------------------------------------------
                ItemProperties item = new ItemProperties(
                        /*
                         * String itemName- name of item.
                         * int itemPrice - the price of item.
                         * int eventDate - when the event took place.
                         * int expenseType - personal, business, nonprofit.
                         * int modeOfPayment- cash, netBanking, card.
                         * String ddmmyyyy- date of entring the data.
                         */
                        itemName,
                        itemPrice,
                        eventDate,
                        expenseType,
                        modeOfPayment,
                        dateMonthYear);

                // adding data to the file
                // ----------------------------------------------------------------
                FileHandler fileHandler = new FileHandler();
                if (restartCondition == false) {
                    fileHandler.save2file(item, "expense_data.csv", what2do);
                } else {
                    fileHandler.append2file(item, "expense_data.csv", what2do);

                }
                restartCondition = monthlyexpense.getBoolData("Do you want to add more data? (true/false): ");
                scnr.nextLine();
            } while (restartCondition);
        }
        if (what2do == 1) {
            scnr.nextLine();
            // ----------------------------------------------------------------
            do {
                String itemName = monthlyexpense.getStringData(
                        "Enter the Name of the Product: ");
                float itemPrice = monthlyexpense.getFloatData(
                        "Enter the Price of the Product: ");
                scnr.nextLine();
                boolean installments = monthlyexpense.getBoolData("is Installment payment available?(true/false): ");
                scnr.nextLine();

                int value2 = monthlyexpense.getIntData(
                        "Enter the mode of payment(0-Cash, 1-NEtBanking, 2-Card): ");
                scnr.nextLine();
                String[] PaymentMode = {
                        "Cash",
                        "NetBanking",
                        "Card"
                };
                String modeOfPayment = PaymentMode[value2];

                int eventDate = monthlyexpense.getIntData(
                        "Enter the Date when the payment/deal was made(DD/MM/YY): ");
                scnr.nextLine();

                int eventDeadline = monthlyexpense.getIntData(
                        "Enter the Deadline of the Payment(DD/MM/YY): ");
                scnr.nextLine();

                int value = monthlyexpense.getIntData(
                        "Enter the expense(0- personal, 1-business, 2-nonProfit)");
                String[] typeOfExpense = {
                        "Personal",
                        "Business",
                        "Non-profit"
                };
                String expenseType = typeOfExpense[value];
                scnr.nextLine();
                Date date = new Date();
                String dateMonthYear = date.toString();
                String[] SplittedDate = dateMonthYear.split(" ");
                dateMonthYear = SplittedDate[0] +
                        SplittedDate[1] +
                        SplittedDate[2] +
                        " " +
                        SplittedDate[5];

                // creates items using constructor2
                // ----------------------------------------------------------------
                ItemProperties item = new ItemProperties(itemName, itemPrice, installments, modeOfPayment, eventDate,
                        eventDeadline, expenseType, dateMonthYear);
                // adding data to the file
                // ----------------------------------------------------------------
                FileHandler fileHandler = new FileHandler();
                if (restartCondition == false) {
                    fileHandler.save2file(item, "debt_data.csv", what2do);
                } else {
                    fileHandler.append2file(item, "debt_data.csv", what2do);

                }
                restartCondition = monthlyexpense.getBoolData("Do you want to add more data? (true/false): ");
                scnr.nextLine();
            } while (restartCondition);
        }
        scnr.close();
    }
}