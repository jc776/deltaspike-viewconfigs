package com.jc776.eeopenid;

import java.util.Set;
import java.util.logging.Logger;

import javax.inject.Inject;

import org.apache.deltaspike.security.api.authorization.AbstractAccessDecisionVoter;
import org.apache.deltaspike.security.api.authorization.AccessDecisionVoterContext;
import org.apache.deltaspike.security.api.authorization.SecurityViolation;

public class CustomAccessDecisionVoter extends AbstractAccessDecisionVoter {
	private static final long serialVersionUID = 1L;

	@Inject
	private Logger logger;
	@Inject
	private SsoSession ssoSession;
	
	@Override
    protected void checkPermission(AccessDecisionVoterContext accessDecisionVoterContext,
            Set<SecurityViolation> violations)
    {
		logger.info("perms" + accessDecisionVoterContext.getSource());
        if(ssoSession.getSubject() == null) {
        	violations.add(newSecurityViolation("must be logged in"));
        }
        
    }

}
