package Controller;

import DB.Customer;
import DB.Global;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class AddCustomer {


    @FXML
    private TextField name;

    @FXML
    private Button add;

    @FXML
    private Button reset;

    @FXML
    private TextField mob;

    @FXML
    private TextField gstno;

    @FXML
    private TextField addr;

    @FXML
    private TextField email;
    @FXML
    private Label warn;

    @FXML
    private TextField state;

    @FXML
    private TextField panno;

    @FXML
    private RadioButton rdreg;

    @FXML
    private RadioButton rdp;

    @FXML
    private RadioButton rdn;

    @FXML
    private RadioButton rdo;



    @FXML
    void initialize() {

        reset.setOnAction(event ->
        {
            name.setText("");
            mob.setText("");
            email.setText("");
            gstno.setText("");
            addr.setText("");
            state.setText("");
            warn.setText("");
            rdreg.setSelected(false);
            rdo.setSelected(false);
            rdp.setSelected(false);
            rdn.setSelected(false);

        });

        rdn.setOnMouseClicked(event1 -> {
            rdo.setSelected(false);
            rdp.setSelected(false);
            rdreg.setSelected(false);
        });
        rdreg.setOnMouseClicked(event1 -> {
            rdo.setSelected(false);
            rdp.setSelected(false);
            rdn.setSelected(false);
        });
        rdp.setOnMouseClicked(event1 -> {
            rdo.setSelected(false);
            rdn.setSelected(false);
            rdreg.setSelected(false);
        });
        rdo.setOnMouseClicked(event1 -> {
            rdn.setSelected(false);
            rdp.setSelected(false);
            rdreg.setSelected(false);
        });


        add.setOnAction(event ->
        {
            String custname =name.getText().trim();
            String mob1 =mob.getText().trim();
            String mail =email.getText().trim();
            String gst =gstno.getText().trim();
            String addres =addr.getText().trim();
            String stat = state.getText().trim();
            String pan =panno.getText().trim();

            if (custname.isEmpty())
                warn.setText("Enter Customer Name");
            else if (mob1.isEmpty())
                 warn.setText("Enter Mobile No.");
            else if(mail.isEmpty())
                warn.setText("Enter mail");
            else if (gst.isEmpty())
                warn.setText("Enter GST");
            else if(mob1.length()!=10)
                warn.setText("Please enter valid number");
            else if(!mail.endsWith("mail.com"))
                warn.setText("Enter valid mail");
            else if( !rdreg.isSelected()&&!rdn.isSelected()&&!rdo.isSelected()&&!rdp.isSelected())
                warn.setText("Please select option");
            else if(pan.length()!=12)
                warn.setText("Enter Correct PAN No.");


            else {

                try {
                    Session session = Global.getSession();
                    Transaction transaction = session.beginTransaction();
                    Customer customer = new Customer();

                    if(stat.isEmpty())
                        warn.setText("Enter value");
                    else
                        customer.setState(stat);

                    if (rdreg.isSelected())
                        customer.setCusttype("Regular");
                    else if(rdp.isSelected())
                        customer.setCusttype("Premium");
                    else if (rdn.isSelected())
                        customer.setCusttype("New");
                    else if(rdo.isSelected())
                        customer.setCusttype("Others");

                    customer.setName(custname);
                    customer.setEmail(mail);
                    customer.setMob(mob1);
                    customer.setGstno(gst);
                    customer.setAddr(addres);
                    customer.setPanno(pan);
                    customer.setD(1);
                    session.persist(customer);
                    transaction.commit();
                    session.close();

                    warn.setText("Customer Added Successfully..!!");
                    name.setText("");
                    mob.setText("");
                    email.setText("");
                    addr.setText("");
                    gstno.setText("");
                    state.setText("");
                    panno.setText("");
                    rdreg.setSelected(false);
                    rdo.setSelected(false);
                    rdp.setSelected(false);
                    rdn.setSelected(false);




                }
                catch (Exception e)
                {
                    warn.setText("Customer already present");
                }
            }

        });

    }
}

