import org.junit.Test;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import com.sun.mail.util.MailSSLSocketFactory;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;


/**
 * @author Master 2021/2/8
 */
public class emailTest {
    public static void main(String[] args) {
        File f = new File("web/static/img/userFace/40d9d19a0fe24e30884fd65b66057f12.jpg");
        System.out.println(f.delete());
    }
}
