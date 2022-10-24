/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package ui;

import model.*;
import model.Event;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

// makes the display which is a menu bar, and two scrollable tables, one listing the IDs of tops, bottoms and
// accessories and the shoe ID and the other listing the different clothing in one's wardrobe
public class OnTheDeskOutfitsGUI extends JPanel implements ActionListener {

    private JFileChooser fc;
    private JMenuBar menuBar;
    private JMenuItem save;
    private JMenuItem load;
    private JMenuItem addClothing;
    private JMenuItem removeClothing;
    private JMenuItem addOutfit;
    private JMenuItem removeOutfit;
    private JMenuItem showOutfitImages;
    private JMenuItem quit;
    private JTable clothingTable;
    private JTable outfitTable;
    protected static JFrame frame;
    private ListOfClothing listOfClothing;
    private OutfitList outfitList;
    private JsonWriter writer;
    private JsonReader reader;

    // Method reformatted from java swing oracle tutorial code sample class in
    // https://docs.oracle.com/javase/tutorial/displayCode.html?code=https://docs.oracle.com/javase/tutorial/uiswing/examples/components/FileChooserDemoProject/src/components/FileChooserDemo.java
    // MODIFIES: this
    // EFFECTS: constructs the list of clothing and outfits, the border layout, and all the swing objects
    //          (a menu bar; menu items for saving, loading, adding clothing and outfits, and removing them;
    //          tables for the clothing and outfit lists), adds the panes to this, and gives menu items ActionListeners
    public OnTheDeskOutfitsGUI() {
        super(new BorderLayout());

        listOfClothing = new ListOfClothing();
        outfitList = new OutfitList();

        writer = new JsonWriter("./data/wardrobe.json");
        reader = new JsonReader("./data/wardrobe.json");

        fc = new JFileChooser();

        makeTables();
        JScrollPane tableScrollPane = new JScrollPane(clothingTable);
        JScrollPane outfitScrollPane = new JScrollPane(outfitTable);

        makeMenuBar();
        save.addActionListener(this);
        load.addActionListener(this);
        quit.addActionListener(this);
        addClothing.addActionListener(this);
        removeClothing.addActionListener(this);
        addOutfit.addActionListener(this);
        removeOutfit.addActionListener(this);
        showOutfitImages.addActionListener(this);

        add(menuBar, BorderLayout.PAGE_START);
        add(outfitScrollPane, BorderLayout.CENTER);
        add(tableScrollPane, BorderLayout.PAGE_END);
    }

    // EFFECTS: individual construction of tables for each list of clothing and list of outfits
    private void makeTables() {
        String[] columnHeader = new String[]{"ID", "Type", "Price", "Store", "Description"};
        clothingTable = new JTable(new DefaultTableModel(columnHeader, 0));
        clothingTable.setPreferredScrollableViewportSize(new Dimension(1500, 500));
        clothingTable.setAutoCreateRowSorter(true);
        clothingTable.setDragEnabled(true);

        outfitTable = new JTable(new DefaultTableModel(new String[]{"Outfit ID", "Tops IDs", "Bottoms IDs", "Shoes ID",
                "Accessories IDs"}, 0));
        outfitTable.setPreferredScrollableViewportSize(new Dimension(1500, 500));
        outfitTable.setFillsViewportHeight(true);
        outfitTable.setAutoCreateRowSorter(true);
    }

    // EFFECTS: individual construction of the menu bar, individual menus, and items
    private void makeMenuBar() {
        menuBar = new JMenuBar();
        save = new JMenuItem("Save");
        load = new JMenuItem("Load");
        quit = new JMenuItem("Quit");
        addClothing = new JMenuItem("Add clothing");
        removeClothing = new JMenuItem("Remove clothing");
        addOutfit = new JMenuItem("Add outfit");
        removeOutfit = new JMenuItem("Remove outfit");
        showOutfitImages = new JMenuItem("Show outfit images");
        JMenu file = new JMenu("File");
        JMenu edit = new JMenu("Edit");
        JMenu select = new JMenu("Select");
        menuBar.add(file);
        menuBar.add(edit);
        menuBar.add(select);
        file.add(save);
        file.add(load);
        file.add(quit);
        edit.add(addClothing);
        edit.add(removeClothing);
        edit.add(addOutfit);
        edit.add(removeOutfit);
        select.add(showOutfitImages);
    }

    // EFFECTS: if a certain menu item is pressed, the respective method to do that action is called
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == save) {
            save();
        } else if (e.getSource() == load) {
            load();
        } else if (e.getSource() == quit) {
            quit();
        } else if (e.getSource() == addClothing) {
            addClothing();
        } else if (e.getSource() == removeClothing) {
            removeClothing();
        } else if (e.getSource() == addOutfit) {
            addOutfit();
        } else if (e.getSource() == removeOutfit) {
            removeOutfit();
        } else if (e.getSource() == showOutfitImages) {
            showOutfitImages();
        }
    }

    private void quit() {
        for (Iterator<Event> it = EventLog.getInstance().iterator(); it.hasNext(); ) {
            Event event = it.next();
            System.out.println(event.getDescription());
        }
        save();
        System.exit(0);
    }

    // EFFECTS: processes all the rows in the clothing table and saves the list to the writer file
    protected void save() {
        try {
            writer.setWriter();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        DefaultTableModel clothingTableModel = (DefaultTableModel) clothingTable.getModel();
        for (int r = 0; r < clothingTableModel.getRowCount(); r++) {
            Clothing clothing = listOfClothing.getClothingByIndex(r);
            clothing.setTypeName((String) clothingTableModel.getValueAt(r,1));
            clothing.setPrice((double) clothingTableModel.getValueAt(r,2));
            clothing.setStoreName((String) clothingTableModel.getValueAt(r,3));
            clothing.setDescription((String) clothingTableModel.getValueAt(r,4));
        }
        writer.writeClothingsAndOutfits(listOfClothing, outfitList);
        writer.close();
    }

    // MODIFIES: listOfClothing, outfitList
    // EFFECTS: gets the clothing and outfit lists from the json file and calls the loadTables method
    private void load() {
        try {
            listOfClothing = reader.readListOfClothing();
            outfitList = reader.readOutfitList();
        } catch (IOException e) {
            e.printStackTrace();
        }
        loadTables();
    }

    // MODIFIES: clothingTable, outfitTable
    // EFFECTS: formats the lists into tables for display
    private void loadTables() {
        for (int i = 0; i < listOfClothing.size(); i++) {
            Clothing clothing = listOfClothing.getClothingByIndex(i);
            DefaultTableModel tableModel = (DefaultTableModel) clothingTable.getModel();
            tableModel.addRow(new Object[]{clothing.getClothingId(), clothing.getClothingType(),
                    clothing.getClothingPrice(), clothing.getClothingStore(), clothing.getClothingDesc()});
            clothingTable.setEditingRow(i);
        }
        for (int i = 0; i < outfitList.size(); i++) {
            Outfit outfit = outfitList.getOutfit(i);
            DefaultTableModel tableModel = (DefaultTableModel) outfitTable.getModel();
            tableModel.addRow(new Object[]{tableModel.getRowCount(), outfit.getListIDs("TOP"),
                    outfit.getListIDs("BOTTOM"), outfit.getShoes(), outfit.getListIDs("ACCESSORY")});
        }
    }

    // MODIFIES: listOfClothing, clothingTable
    // EFFECTS: makes popups for user to input various data values to add a clothing to the list
    private void addClothing() {
        Clothing clothing = new Clothing();
        String [] type = {"TOP", "BOTTOM", "SHOES", "ACCESSORY"};
        String typeName = String.valueOf(JOptionPane.showInputDialog(frame, "Choose one type", "Type",
                JOptionPane.PLAIN_MESSAGE, null, type, type[0]));
        clothing.setTypeName(typeName);
        clothing.setPrice(parseDouble(JOptionPane.showInputDialog(frame, "Enter price.",
                "Price", JOptionPane.PLAIN_MESSAGE)));
        clothing.setStoreName(JOptionPane.showInputDialog(frame, "Enter the store you got the clothing from.",
                "Store", JOptionPane.PLAIN_MESSAGE));
        clothing.setDescription(JOptionPane.showInputDialog(frame, "Describe your clothing.",
                "Description", JOptionPane.PLAIN_MESSAGE));
        JOptionPane.showMessageDialog(frame, "Choose file from desktop.");
        getFileFromDesktop(clothing);
        DefaultTableModel tableModel = (DefaultTableModel) clothingTable.getModel();
        tableModel.addRow(new Object[]{clothing.getClothingId(), clothing.getClothingType(),
                clothing.getClothingPrice(), clothing.getClothingStore(), clothing.getClothingDesc()});
        getImage(clothing);
        clothingTable.setEditingRow(clothingTable.getRowCount() - 1);
        listOfClothing.addClothing(clothing);
    }

    // EFFECTS: opens the image file stored in code files
    public void getImage(Clothing clothing) {
        File file = new File(clothing.getPath());
        try {
            Desktop.getDesktop().open(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // EFFECTS: gets the url of the image the user wants to link to the clothing and calls the open function
    public void getFileFromDesktop(Clothing clothing) {
        int returnVal = fc.showOpenDialog(OnTheDeskOutfitsGUI.this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            URL imgURL = null;
            try {
                imgURL = file.toURL();
            } catch (MalformedURLException ex) {
                ex.printStackTrace();
            }
            try {
                setImage(imgURL, clothing);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Open command cancelled by user.");
        }
    }

    // EFFECTS: copies the image onto a buffered image object and calls the file making method
    public void setImage(URL imageURL, Clothing clothing) {
        try {
            BufferedImage bi = ImageIO.read(imageURL);
            makeFile(bi, clothing);
        } catch (IOException e) {
            System.out.println("Couldn't find file.");
        }
    }

    // EFFECTS: makes a new file in the data package with a name based on the clothing type and ID
    public void makeFile(BufferedImage bi, Clothing clothing) {
        try {
            ImageIO.write(bi, "png", new File(clothing.getPath()));
        } catch (IOException e) {
            System.out.println("Couldn't find file.");
        }
    }

    // MODIFIES: clothingTable, listOfClothing
    // EFFECTS: removes a clothing from the list and from the clothing table
    private void removeClothing() {
        DefaultTableModel clothingTableModel = (DefaultTableModel) clothingTable.getModel();
        if (clothingTable.isRowSelected(clothingTable.getSelectedRow())) {
            int id = parseInt(JOptionPane.showInputDialog(frame, "Enter ID of clothing you want to remove.",
                    "Clothing: remove", JOptionPane.PLAIN_MESSAGE));
            clothingTableModel.removeRow(clothingTable.getSelectedRow());
            File file = new File(listOfClothing.getClothing(id).getPath());
            file.delete();
            listOfClothing.removeClothing(id);
        } else {
            JOptionPane.showMessageDialog(frame, "No selected row found.");
        }
    }

    // MODIFIES: outfitList, outfitTable
    // EFFECTS: added an outfit to the list and the outfit table
    private void addOutfit() {
        Outfit outfit = new Outfit();
        addClothingsToOutfit(outfit, "Enter number of tops you want to add to an outfit.",
                "Outfit: number of tops", "Enter ID of top you want to add to an outfit.",
                "Outfit: TOP", "TOP");
        addClothingsToOutfit(outfit, "Enter number of bottoms you want to add to an outfit.",
                "Outfit: number of bottoms", "Enter ID of bottom you want to add to an outfit.",
                "Outfit: BOTTOM", "BOTTOM");
        int id = parseInt(JOptionPane.showInputDialog(frame, "Enter ID of shoes you want to add to an outfit.",
                "Outfit: SHOES", JOptionPane.PLAIN_MESSAGE));
        outfit.setShoes(id);
        addClothingsToOutfit(outfit, "Enter number of accessories you want to add to "
                        + "an outfit.","Outfit: number of accessories",
                "Enter ID of accessory you want to add to an outfit.", "Outfit: ACCESSORY",
                "ACCESSORY");
        DefaultTableModel tableModel = (DefaultTableModel) outfitTable.getModel();
        tableModel.addRow(new Object[]{tableModel.getRowCount(), outfit.getListIDs("TOP"),
                outfit.getListIDs("BOTTOM"), outfit.getShoes(), outfit.getListIDs("ACCESSORY")});
        outfitList.add(outfit);
    }

    // EFFECTS: helper function to display the input popup box depending on the clothing type
    private void addClothingsToOutfit(Outfit outfit, String firstMessage, String firstTitle, String secondMessage,
                                      String secondTitle, String type) {
        int num = parseInt(JOptionPane.showInputDialog(frame, firstMessage,
                firstTitle, JOptionPane.PLAIN_MESSAGE));
        for (int i = 0; i < num; i++) {
            int id = parseInt(JOptionPane.showInputDialog(frame, secondMessage,
                    secondTitle, JOptionPane.PLAIN_MESSAGE));
            if (type.equals("TOP")) {
                outfit.setTop(listOfClothing.getClothing(id));
            } else if (type.equals("BOTTOM")) {
                outfit.setBottom(listOfClothing.getClothing(id));
            } else {
                outfit.setAccessory(listOfClothing.getClothing(id));
            }
        }
    }

    // MODIFIES: outfitList, outfitTable
    // EFFECTS: removes an outfit from the list and outfit table
    private void removeOutfit() {
        int id = parseInt(JOptionPane.showInputDialog(frame, "Enter ID of outfit you want to remove.",
                "Outfit: remove", JOptionPane.PLAIN_MESSAGE));
        outfitList.remove(id);
        DefaultTableModel outfitTableModel = (DefaultTableModel) outfitTable.getModel();
        outfitTableModel.removeRow(id);
    }

    // EFFECTS: opens all the images linked to every clothing in the outfit calling the helper methods for multiple
    //          clothings of one type
    private void showOutfitImages() {
        int row = outfitTable.getSelectedRow();
        if (row != -1) {
            Outfit outfit = outfitList.getOutfit(row);
            showListOfClothingImages(outfit.getTops());
            showListOfClothingImages(outfit.getBottoms());
            try {
                Desktop.getDesktop().open(new File(listOfClothing.getClothing(outfit.getShoes()).getPath()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            showListOfClothingImages(outfit.getAccessories());
        } else {
            JOptionPane.showMessageDialog(frame, "Please select an outfit.");
        }
    }

    // EFFECTS: opens the image linked to a clothing for every clothing in the list
    private void showListOfClothingImages(ListOfClothing listOfClothing) {
        for (int i = 0; i < listOfClothing.size(); i++) {
            try {
                Desktop.getDesktop().open(new File(listOfClothing.getClothingByIndex(i).getPath()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Method taken from java swing oracle tutorial code sample class in
    // https://docs.oracle.com/javase/tutorial/displayCode.html?code=https://docs.oracle.com/javase/tutorial/uiswing/examples/components/FileChooserDemoProject/src/components/FileChooserDemo.java
    // MODIFIES: frame
    // EFFECTS: constructs the frame object and calls this constructor and displays all the components on the frame
    protected static void createAndShowGUI() {
        //Create and set up the window.
        frame = new JFrame("App");
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        //Add content to the window.
        frame.add(new OnTheDeskOutfitsGUI());

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
}