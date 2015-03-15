package controllers;

import com.shopservice.MailService;
import play.mvc.Controller;
import play.mvc.Result;

import javax.mail.MessagingException;
import java.util.Arrays;
import java.util.List;

public class FeedbackController extends Controller {
    private static final List<String> OWNERS = Arrays.asList("jmen7070@gmail.com");

    public static Result feedbackNotify(){

        String name = request().body().asFormUrlEncoded().get("contact_name")[0];
        String email = request().body().asFormUrlEncoded().get("contact_email")[0];
        String phone = request().body().asFormUrlEncoded().get("contact_phone")[0];
        String message = request().body().asFormUrlEncoded().get("contact_message")[0];

        StringBuilder body = new StringBuilder("Visitor, " + name + "\n").append("Phone: " + phone);
        if ((email != null) && (!email.trim().equalsIgnoreCase("")))
            body.append(", email: " + email );
        if ((message != null) && (!message.trim().equalsIgnoreCase("")))
            body.append("\n" + message );

        try {
            MailService.getInstance().report("from a visitor", body.toString(), OWNERS);
            return ok();

        } catch (MessagingException e) {
            e.printStackTrace();
            return internalServerError();
        }


    }
}
