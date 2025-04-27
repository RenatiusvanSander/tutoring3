package edu.remad.tutoring3.events.authentication;

import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

import edu.remad.tutoring3.events.RegisterJwtAuticatedUser;

@Component
public class JwtAuthenticationEvents {

	@EventListener
    public RegisterJwtAuticatedUser onSuccess(AuthenticationSuccessEvent success) {
		System.out.println("###### received RegisterJwtAuticatedUser");
		return new RegisterJwtAuticatedUser();
    }

    @EventListener
    public void onFailure(AbstractAuthenticationFailureEvent failures) {
		// do nothing
    }
}
