/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

import tools.MyTool;

/**
 *
 * @author minhn
 */
public class DealerList extends ArrayList<Dealer> {
    private String dataFile = "";
    boolean changed = false;

    public void loadDealerFromFile() {
        try {
            FileReader fr = new FileReader(dataFile);
            BufferedReader br = new BufferedReader(fr);
            String info;
            while ((info = br.readLine()) != null) {
                StringTokenizer st = new StringTokenizer(info, ",");
                String ID = st.nextToken();
                String name = st.nextToken();
                String addr = st.nextToken();
                String phone = st.nextToken();
                boolean continuing = Boolean.parseBoolean(st.nextToken());
                this.add(new Dealer(ID, name, addr, phone, continuing));
            }
            br.close();
            fr.close();
        } catch (Exception e) {
            // System.out.println(e);
        }
    }

    public void initWithFile() {
        Config cR = new Config();
        dataFile = cR.getDealerFile();
    }

    public DealerList() {
        initWithFile();
        loadDealerFromFile();
    }

    // TODO: Menu Functions
    public int checkID(String id) {
        if (this.isEmpty()) {
            return -1;
        }
        for (int i = 0; i < this.size(); i++) {
            if (this.get(i).getID().equalsIgnoreCase(id)) {
                return i;
            }
        }
        return -1;
    }

    public void addDealer() {
        int index;
        String ID;
        do {
            ID = MyTool.getStringWithRegex("ID of new dealer(Dxxx):", "Wrong ID format. Please try again.",
                    "^[D]\\d{3}$");
            index = checkID(ID);
            if (index >= 0) {
                System.out.println("ID is duplicated.Input again.");
            }
        } while (index >= 0);
        String name = MyTool.getString("Name of new dealer:", "Not blank or empty.");
        String addr = MyTool.getString("Address of new dealer:", "Not blank or empty.");
        String phone = MyTool.getStringWithRegex("Phone of new dealer:", "Phone is 9 or 11 digit!!",
                "\\d{9}|\\d{11}");
        boolean continuing = true;
        this.add(new Dealer(ID, name, addr, phone, continuing));
        System.out.println("New dealer added successfully");
        changed = true;
    }

    public void searchDealer() {
        String ID = MyTool.getString("Enter ID dealer to search:", "Not blank or empty.");
        int index = checkID(ID);
        if (index >= 0) {
            this.get(index).output();
        } else {
            System.out.println("Dealer with ID " + ID + " is not found.");
        }
    }

    public void removeDealer() {
        String ID = MyTool.getString("Enter ID dealer to remove: ", "Not blank or empty.");
        int index = checkID(ID);
        if (index >= 0) {
            this.get(index).setContinuing(false);
            System.out.println("Successfully.");
            changed = true;
        } else {
            System.out.println("Dealer with ID " + ID + " is not found!");
        }
    }

    public void updateDealer() {
        String ID = MyTool.getString("Enter ID dealer to update: ", "Not blank or empty.");
        int index = checkID(ID);
        if (index >= 0) {
            System.out.print("Enter new dealer name to update: ");
            String newName = MyTool.sc.nextLine().trim();
            if (!newName.isEmpty()) {
                this.get(index).setName(newName);
                changed = true;
            }
            System.out.print("Enter new dealer address to update: ");
            String newAddress = MyTool.sc.nextLine().trim();
            if (!newAddress.isEmpty()) {
                this.get(index).setAddr(newAddress);
                changed = true;
            }
            System.out.print("Enter new dealer phone number to update: ");
            String newPhone = MyTool.sc.nextLine().trim();
            if (!newPhone.isEmpty()) {
                if (newPhone.matches("\\d{9}|\\d{11}")) {
                    this.get(index).setPhone(newPhone);
                    changed = true;
                } else {
                    System.out.println("Wrong phone format.");
                    newPhone = MyTool.getStringWithRegex("Enter new dealer phone number to update: ",
                            "Wrong phone format.",
                            "\\d{9}|\\d{11}");
                    this.get(index).setPhone(newPhone);
                    changed = true;
                }
            }
            System.out.println("Enter new dealer continuing status to update: ");
            String newContinuing = MyTool.getTrueFalse();
            if (!newContinuing.isEmpty()) {
                if (newContinuing == "true") {
                    this.get(index).setContinuing(true);
                    changed = true;
                } else {
                    this.get(index).setContinuing(false);
                    changed = true;
                }
            }
            if (changed == true) {
                System.out.println("The dealer's information has been updated.");
            } else {
                System.out.println("The dealer's information not changed.");
            }

        } else {
            System.out.println("Dealer with ID " + ID + " is not found!");
        }
    }

    public void printAllDealers() {
        if (this.isEmpty()) {
            System.out.println("List empty.Nothing to print.");
        } else {
            System.out.println(
                    "----------------------------------- LIST OF ALL DEALERS -----------------------------------");
            Collections.sort(this);
            for (int i = 0; i < this.size(); i++) {
                this.get(i).output();
            }
            System.out.println(
                    "------------------------------------------ END --------------------------------------------");
        }
    }

    public void printContinuingDealers() {
        if (this.isEmpty()) {
            System.out.println("List empty.Nothing to print.");
        } else {
            System.out.println(
                    "----------------------------- LIST OF ALL CONTINUING DEALERS -----------------------------");
            for (int i = 0; i < this.size(); i++) {
                if (this.get(i).isContinuing() == true) {
                    this.get(i).output();
                }
            }
            System.out.println(
                    "------------------------------------------ END -------------------------------------------");
        }
    }

    public void printUnContinuingDealers() {
        if (this.isEmpty()) {
            System.out.println("List empty.Nothing to print.");
        } else {
            System.out.println(
                    "--------------------------- LIST OF ALL UN-CONTINUING DEALERS ---------------------------");
            for (int i = 0; i < this.size(); i++) {
                if (this.get(i).isContinuing() == false) {
                    this.get(i).output();
                }
            }
            System.out.println(
                    "----------------------------------------- END -------------------------------------------");
        }
    }

    public void writeDealerToFile() {
        if (changed) {
            try {
                File f = new File(dataFile);
                FileWriter fw = new FileWriter(f);
                PrintWriter pw = new PrintWriter(fw);
                for (int i = 0; i < this.size(); i++) {
                    pw.println(this.get(i).toString());
                }
                pw.close();
                fw.close();
            } catch (Exception e) {
                System.out.println(e);
            }
            changed = false;
            System.out.println("Save to file successfully.");
        }
    }

    public boolean isChanged() {
        return changed;
    }

    public void setChanged(boolean changed) {
        this.changed = changed;
    }

}
