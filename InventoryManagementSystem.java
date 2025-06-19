import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;

public class InventoryManagementSystem extends JFrame implements ActionListener {
    JTextField tfName = new JTextField(10);
    JTextField tfQty = new JTextField(5);
    JButton btnAdd = new JButton("Add");
    JButton btnUpdate = new JButton("Update");
    JButton btnDelete = new JButton("Delete");
    JButton btnView = new JButton("View All");
    JComboBox<String> cbCat = new JComboBox<>(new String[]{"Electronics", "Grocery", "Clothing"});
    JTextArea output = new JTextArea(10, 40);
    DefaultListModel<String> model = new DefaultListModel<>();
    JList<String> itemList = new JList<>(model);

    Connection conn;

    public InventoryManagementSystem() {
        setTitle("Inventory Manager");
        setSize(600, 500);
        setLayout(new FlowLayout());

        add(new JLabel("Item Name"));
        add(tfName);
        add(new JLabel("Qty"));
        add(tfQty);
        add(new JLabel("Category"));
        add(cbCat);
        add(btnAdd);
        add(btnUpdate);
        add(btnDelete);
        add(btnView);
        add(new JScrollPane(itemList));
        add(new JScrollPane(output));

        btnAdd.addActionListener(this);
        btnUpdate.addActionListener(this);
        btnDelete.addActionListener(this);
        btnView.addActionListener(this);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/inventory", "root", "password");
        } catch (Exception e) {
            output.setText("DB Connection Error: " + e.getMessage());
        }

        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void actionPerformed(ActionEvent e) {
        String name = tfName.getText();
        int qty = Integer.parseInt(tfQty.getText());
        String cat = (String) cbCat.getSelectedItem();

        if (e.getSource() == btnAdd) {
            try {
                PreparedStatement ps = conn.prepareStatement("INSERT INTO items (name, quantity, category) VALUES (?, ?, ?)");
                ps.setString(1, name);
                ps.setInt(2, qty);
                ps.setString(3, cat);
                ps.executeUpdate();
                output.append("Item Added Successfully\n");
            } catch (SQLException ex) {
                output.append("SQL Error: " + ex.getMessage() + "\n");
            }
        }

        if (e.getSource() == btnUpdate) {
            try {
                int id = itemList.getSelectedIndex() + 1;
                PreparedStatement ps = conn.prepareStatement("UPDATE items SET name=?, quantity=?, category=? WHERE id=?");
                ps.setString(1, name);
                ps.setInt(2, qty);
                ps.setString(3, cat);
                ps.setInt(4, id);
                ps.executeUpdate();
                output.append("Item Updated Successfully\n");
            } catch (SQLException ex) {
                output.append("Update Error: " + ex.getMessage() + "\n");
            }
        }

        if (e.getSource() == btnDelete) {
            try {
                int id = itemList.getSelectedIndex() + 1;
                PreparedStatement ps = conn.prepareStatement("DELETE FROM items WHERE id=?");
                ps.setInt(1, id);
                ps.executeUpdate();
                output.append("Item Deleted Successfully\n");
            } catch (SQLException ex) {
                output.append("Delete Error: " + ex.getMessage() + "\n");
            }
        }

        if (e.getSource() == btnView) {
            try {
                model.clear();
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM items");
                while (rs.next()) {
                    String record = rs.getInt("id") + ": " + rs.getString("name") + " - " + rs.getInt("quantity") + " - " + rs.getString("category");
                    model.addElement(record);
                }
                output.append("Displayed All Records\n");
            } catch (SQLException ex) {
                output.append("View Error: " + ex.getMessage() + "\n");
            }
        }

        InventoryItem item = new InventoryItem(new Random().nextInt(1000), name, qty, cat);
        ItemThread t1 = new ItemThread(item);
        t1.setPriority(Thread.MAX_PRIORITY);
        t1.start();

        new Thread(new DBLogger()).start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new InventoryManagementSystem());
    }
}
