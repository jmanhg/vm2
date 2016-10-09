
package mx.com.oposicion.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.mail.Address;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.apache.log4j.Logger;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

/**
 * <p>Descripción:</p> Clase que implementa el servicio de mail
 *
 * @author GAA
 * @version 1.1.56
 */
public class MailEngine {
    private final static Logger LOGGER = Logger.getLogger(MailEngine.class);
    
    private List<MimeMessage> mimeMessagesBag = new ArrayList<MimeMessage>();
    private static MailEngine instance = new MailEngine();
    private JavaMailSenderImpl mailSender = null;
    
    private boolean active;
    private InternetAddress from;
    private static String GET_FULL_CONTEXT;
    private static int MAX_QUEUE_SIZE = 20;

    /**
     * En cumplimiento de la firma que el petrón Singleton demanda para el constructor de la clase
     */
    private MailEngine() {
    }

    public static MailEngine getInstance(String fullContext, int maxQueueSize) {
        GET_FULL_CONTEXT = fullContext;
        if(maxQueueSize < 1) {
            LOGGER.warn("No se especificó el tamaño máximo de la bolsa de correos, se usará un buffer de "+MAX_QUEUE_SIZE+" como tamaño por defecto");
        } else {
            MAX_QUEUE_SIZE = maxQueueSize;
        }
        return instance;
    }
    
    public JavaMailSenderImpl getMailSender() {
        return mailSender;
    }

    public void setMailSender(JavaMailSenderImpl mailSender) {
        this.mailSender = mailSender;
    }

    public void setFrom(InternetAddress from) {
        this.from = from;
    }

    public InternetAddress getFrom() {
        return this.from;
    }

    public boolean getActive() {
        return this.active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     *
     * @param to
     * @param subject
     * @param text
     */
    public void addMessage(String to, String subject, String text) {
        addMessage(to, subject, text, new File[0]);
    }

    /**
     *
     * @param to []
     * @param subject
     * @param text
     */
    public void addMessage(String[] to, String subject, String text) {
        addMessage(to, null, subject, text, new File[0]);
    }

    /**
     *
     * @param to
     * @param bcc
     * @param subject
     * @param text
     */
    public void addMessage(String to, String bcc, String subject, String text) {
        addMessage(to, bcc, subject, text, new File[0]);
    }

    /**
     *
     * @param to
     * @param subject
     * @param text
     * @param attachments []
     */
    public void addMessage(String to, String subject, String text, File... attachments) {
        String[] too = {to};
        addMessage(too, null, subject, text, attachments);
    }

    /**
     *
     * @param to []
     * @param bcc []
     * @param subject
     * @param text
     * @param attachments []
     */
    public void addMessage(String to, String[] bcc, String subject, String text, File... attachments) {
        String[] too = {to};
        addMessage(too, bcc, subject, text, attachments);
    }

    /**
     *
     * @param to
     * @param bcc
     * @param subject
     * @param text
     * @param attachments []
     */
    public void addMessage(String to, String bcc, String subject, String text, File... attachments) {
        String[] too = {to};
        String[] bccArray = {bcc};
        addMessage(too, bccArray, subject, text, attachments);
    }

    /**
     *
     * @param to []
     * @param bcc []
     * @param subject
     * @param text
     * @param attachments []
     */
    public void addMessage(String[] to, String[] bcc, String subject, String text, File... attachments) {
        addMsg(to, bcc, subject, text, attachments);
    }

    /**
     * Envía un conjunto de correos que han sido almacenados en un contenedor
     * temporal administrado por spring
     */
    public void sendAllMessages() {
        try {
            this.sendAllMessagesHelper();
        } catch (Exception e) {
            LOGGER.error("Error en el servicio de envio de correos electronicos: " + e.getMessage());
        }
    }

    /**
     * Arma una cadena de un url adecuado y ajustado al contexto actual del
     * aplicativo que incluye un parámetro llamado 'sid' igualado a una llave
     * aleatoria
     *
     * @param context
     * @param key
     * @return String : url armado acorde al contexto actua
     */
//	public static String forgotPasswordURL(String context, String key) {
//		return buildURL(context, key, "content/common/forgotPassword.jsf?sid=", "Recupera claves");
//	}
    /**
     * Arma una cadena de un url adecuado y ajustado al contexto actual del
     * aplicativo que incluye un parámetro llamado 'sid' igualado a una llave
     * aleatoria
     *
     * @param context
     * @param key
     * @return String : url armado acorde al contexto actua
     */
//	public static String solicitaRegistroURL(String context, String key) {
//		return buildURL(context, key, "content/common/registro/CompletaRegistro.jsf?sid=", "Completar registro");
//	}
    /**
     * Arma una cadena de un url adecuado y ajustado al contexto actual del
     * aplicativo que incluye un parámetro llamado 'sid' igualado a una llave
     * aleatoria
     *
     * @param key
     * @param path
     * @param message
     * @return String : url armado acorde al contexto actua
     */
    public static String buildURL(String key, String path, String message) {
        StringBuilder url = new StringBuilder();
        url.append("<a href='http:/"); // con una sola diagonal, ya que abajo se pone la otra
        url.append(GET_FULL_CONTEXT); // P ej "/localhost:8080/skeleton/"
        url.append(path);
        url.append(key);
        url.append("'>");
        url.append(message);
        url.append("</a>");
        return url.toString();
    }

    /**
     * Arma una cadena de un url adecuado y ajustado al contexto actual del
     * aplicativo que incluye un parámetro llamado 'sid' igualado a una llave
     * aleatoria
     *
     * @param key
     * @return String : url armado acorde al contexto actua
     */
    public static String armaURLRegister(String key) {
        StringBuilder url = new StringBuilder();
        url.append("<a href='http:/"); // con una sola diagonal, ya que abajo se pone la otra
        url.append(GET_FULL_CONTEXT); // P ej "/localhost:8080/skeleton/"
        url.append("content/common/registro/RegUsuario.jsf?sid=");
        url.append(key);
        url.append("'>Confirma registro</a>");
        return url.toString();
    }

    /*
     * Utilería que revisa si la estructura de una correo electrónico es
     * correcta o no. En caso negativo, dispara una Excepción con tal
     * notificación. En caso afirmativo, termina sin mas aviso.
     *
     * @param mailCandidate @throws Exception
     */
//	public static void checkMailStructure(String mailCandidate) throws Exception {
//		if(mailCandidate.trim().length()<5) {
//			throw new Exception("Un correo debe tener más de 4 caracteres");
//		}
//		
//		if(!ContextUtils.evalMalformedMail(mailCandidate)) {
//			throw new Exception("Correo mal formado, revise la estructura de su correo");
//		}
//	}
    public static String getText(String filename) {
        StringBuilder sb = new StringBuilder();
        try {
            FileReader fr = new FileReader(filename);
            int n;
            while ((n = fr.read()) != -1) {
                sb.append((char) n);
            }
            fr.close();
        } catch (FileNotFoundException ex) {
            LOGGER.error("Error al recuperar texto", ex);
        } catch (IOException ex) {
            LOGGER.error("Error al recuperar texto", ex);
        }
        return sb.toString();
    }

    /**
     * Retorna el texto contenido en el archivo que le es pasado como parámetro
     * formal. Busca el archivo a partir de la raiz del proyecto WEB.
     *
     * OJO La ruta: 'fileToBeRead' debe venir SIN '/' al principio ya que
     * Starter.getRealPath() trae '/' al final.
     */
    public static String getText(String realPath, String fileToBeRead) {
        return getText(realPath + fileToBeRead);
    }

    /**
     * Utilería de apoyo al envío (posiblemente masivo) de mensajes de correo
     * electrónico. Detecta si es requerido enviar mensajes y no en función del
     * estado de la bolsa de mensajes encolados. Adicionalmente, si la bandera
     * de 'activo' está en falso, sólo imprime en el log lo que se hubiera
     * enviado por correo, pero realmente no ocurre tal envío.
     *
     * @throws Exception
     */
    private void sendAllMessagesHelper() throws Exception {
        if (mimeMessagesBag != null && mimeMessagesBag.size() > 0) {
            MimeMessage[] mimeMessages = mimeMessagesBag.toArray(new MimeMessage[1]);
            if (active) {
                String fecha = "10-05-2012_1143";//ContextUtils.formatearFecha(new Date(), ContextUtils.FORMATO_DDMMYYYYHHMMSS);
                LOGGER.info("Enviando: " + mimeMessages.length + " mensajes. Con fecha: " + fecha);
                mailSender.send(mimeMessages);
            } else {
                LOGGER.info("El servicio de notificación de correo electrónico está inactivo !!!");
                for (MimeMessage mimeMessage : mimeMessages) {
                    LOGGER.info("Subject: " + mimeMessage.getSubject());
                    Address[] recipients = mimeMessage.getRecipients(RecipientType.TO);
                    for (Address addr : recipients) {
                        LOGGER.info("--->" + addr.toString());
                    }
                }
            }
            mimeMessagesBag.clear();
            LOGGER.info("Mensajes enviados !!!!!!!!!");
        }
    }

    /**
     * Envío de email con varios attachments
     *
     * @param to correo electrónico del destinatario
     * @param subject asunto del mensaje
     * @param text cuerpo del mensaje
     * @param attachments ficheros que se anexarán al mensaje
     */
    private void addMsg(String[] to, String[] bcc, String subject, String text, File... attachments) {
        // asegurando la trazabilidad  
        if (LOGGER.isDebugEnabled()) {
            final boolean usingPassword = !"".equals(mailSender.getPassword());
            LOGGER.debug("Sending email to: '" + to
                    + "' [through host: '" + mailSender.getHost() + ":" + mailSender.getPort()
                    + "', username: '" + mailSender.getUsername()
                    + "' usingPassword:" + usingPassword + "].");
        }

        // plantilla para el envío de email  
        final MimeMessage message = mailSender.createMimeMessage();

        try {
            // el flag a true indica que va a ser multipart  
            final MimeMessageHelper helper = new MimeMessageHelper(message, true);

            // settings de los parámetros del envío  
            //message.setContent(mensaje, "text/html; charset=\"big5\"");
            helper.setTo(to);
            if (bcc != null) {
                helper.setBcc(bcc);
            }
            helper.setSubject(subject);
            helper.setFrom(getFrom());

            // Hola                                    <pic>content/themes/images/icon_twitter.png</pic> Mundo
            // <img src='http://www.gus.com:8080/aplicativo/content/themes/images/icon_twitter.png' />
            String reemplazo = text;
            reemplazo = reemplazo.replaceAll("<pic>", "<img src='http:/" + GET_FULL_CONTEXT);
            reemplazo = reemplazo.replaceAll("</pic>", "' />");
            helper.setText(reemplazo, true);

            // adjuntando los archivos indicados  
            if (attachments != null && attachments.length > 0) {
                for (int i = 0; i < attachments.length; i++) {
                    FileSystemResource file = new FileSystemResource(attachments[i]);
                    helper.addAttachment(attachments[i].getName(), file);
                    if (LOGGER.isDebugEnabled()) {
                        LOGGER.debug("File '" + file + "' attached.");
                    }
                }
            }

        } catch (MessagingException e) {
            new RuntimeException(e);
        }
        mimeMessagesBag.add(message);
        
        if(mimeMessagesBag.size() >= MAX_QUEUE_SIZE) {
            LOGGER.info("La bolsa de correos ha llegado al límite permitido por la aplicación, se iniciará el envío de correos");
            sendAllMessages();
        }
    }
//     private static String getFullContext() {
//    	 return ContextUtils.getFullContext(MailEngine.trunk);
//     }
//     
//     private static String trunk = "localhost:8080";
//     public static void setTrunk(String trunk) {
//    	 MailEngine.trunk = trunk; 
//     }
}// ends class


