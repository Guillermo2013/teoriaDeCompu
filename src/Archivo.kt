import java.awt.event.ActionEvent
import java.io.*
import javax.swing.JButton
import javax.swing.JFileChooser
import javax.swing.JLabel
import javax.swing.JTextField



import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.StringReader;

/**
 *
 * @author dancastillo
 */
public class Archivo : javax.swing.JFrame() {

    /**
     * Creates new form cus
     */
    var data: String =""
    var chooser:JFileChooser = JFileChooser();

    init {

        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    fun initComponents() {

        jLabel1 = javax.swing.JLabel();
        jLabel2 = javax.swing.JLabel();
        jLabel3 = javax.swing.JLabel();
        jLabel4 = javax.swing.JLabel();
        jLabel5 = javax.swing.JLabel();
        txtCompany = javax.swing.JTextField();
        txtContact = javax.swing.JTextField();
        txtAddress = javax.swing.JTextField();
        txtPhone = javax.swing.JTextField();
        txtEmail = javax.swing.JTextField();
        btnSave = javax.swing.JButton();
        btnReset = javax.swing.JButton();
        btnExit = javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        (jLabel1 as JLabel).setText("Company:");

        (jLabel2 as JLabel).setText("Contact:");

        (jLabel3 as JLabel).setText("Address:");

        (jLabel4 as JLabel).setText("Phone:");

        (jLabel5 as JLabel).setText("E-Mail:");

        (btnSave as JButton).setText("Save");
        (btnSave as JButton).addActionListener({ evt:java.awt.event.ActionEvent->
                btnSaveActionPerformed(evt);

        });

        (btnReset as JButton).setText("Reset");
        (btnReset as JButton).addActionListener({evt: ActionEvent ->
            btnSaveActionPerformed(evt);
            });




        /**
         * @param args the command line arguments
         */
    }
    fun btnSaveActionPerformed(evt:java.awt.event.ActionEvent ) {
        // TODO add your handling code here:
        var retValue = chooser.showSaveDialog (null);
        if (retValue == chooser.approveButtonMnemonic) {
            var f = chooser . getSelectedFile ();
            try {
                var reader:BufferedReader =  BufferedReader( StringReader (data));//data is string to save
                var writer: BufferedWriter =  BufferedWriter( FileWriter (f));
                var str: String = ""
                while (str  != null) {
                    str = reader.readLine()
                    writer.write(str + System.getProperty("line.separator"));
                }
            } catch(ex:Exception ) {
                System.out.println("Error!");
            }
        }
    }

    fun btnResetActionPerformed(evt:java.awt.event.ActionEvent ) {
        // TODO add your handling code here:
        (txtCompany as JTextField).setText("");
        (txtContact as JTextField).setText("");
        (txtAddress as JTextField).setText("");
        (txtPhone as JTextField).setText("");
        (txtEmail as JTextField).setText("");

    }

        // Variables declaration - do not modify
        var btnExit: JButton? = null
    var btnReset: JButton? = null
    var btnSave: JButton? = null
    var jLabel1: JLabel? = null
    var jLabel2: JLabel? = null
    var jLabel3: JLabel? = null
    var jLabel4: JLabel? = null
    var jLabel5: JLabel? = null
    var txtAddress: JTextField? = null
    var txtCompany: JTextField? = null
    var txtContact: JTextField? = null
    var txtEmail: JTextField? = null
    var txtPhone: JTextField? = null
    // End of variables declaration
    }