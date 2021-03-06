import com.demo.security.customEvent.AuthenticationFailureUserNotFoundEvent
import com.demo.security.filter.ApplicationAuthenticationFilter
import com.demo.security.handler.AuthenticationSuccessHandler
import com.demo.security.listener.ApplicationSecurityEventListener
import com.demo.security.service.ApplicationGormUserDetailsService
import grails.plugin.springsecurity.SpringSecurityUtils
import grails.plugin.springsecurity.userdetails.NoStackUsernameNotFoundException
import org.springframework.security.authentication.DefaultAuthenticationEventPublisher
import org.springframework.security.authentication.encoding.PlaintextPasswordEncoder

// Place your Spring DSL code here
beans = {
    def conf = SpringSecurityUtils.securityConfig

    passwordEncoder(PlaintextPasswordEncoder)

    //To authorize login request
    authenticationProcessingFilter(ApplicationAuthenticationFilter) {
        authenticationManager = ref('authenticationManager')
        sessionAuthenticationStrategy = ref('sessionAuthenticationStrategy')
        authenticationSuccessHandler = ref('authenticationSuccessHandler')
        authenticationFailureHandler = ref('authenticationFailureHandler')
        rememberMeServices = ref('rememberMeServices')
        authenticationDetailsSource = ref('authenticationDetailsSource')
        requiresAuthenticationRequestMatcher = ref('filterProcessUrlRequestMatcher')
        usernameParameter = conf.apf.usernameParameter
        passwordParameter = conf.apf.passwordParameter
        continueChainBeforeSuccessfulAuthentication = conf.apf.continueChainBeforeSuccessfulAuthentication
        allowSessionCreation = conf.apf.allowSessionCreation
        postOnly = conf.apf.postOnly
        storeLastUsername = conf.apf.storeLastUsername
    }

    authenticationSuccessHandler(AuthenticationSuccessHandler) {
        requestCache = ref('requestCache')
        defaultTargetUrl = conf.successHandler.defaultTargetUrl
        alwaysUseDefaultTargetUrl = conf.successHandler.alwaysUseDefault
        targetUrlParameter = conf.successHandler.targetUrlParameter
        useReferer = conf.successHandler.useReferer
        redirectStrategy = ref('redirectStrategy')
        superUserUrl = conf.successHandler.superUserUrl
        customerUserUrl = conf.successHandler.customerUserUrl
    }

    applicationSecurityEventListener(ApplicationSecurityEventListener) {
    }

    authenticationEventPublisher(DefaultAuthenticationEventPublisher) {
        additionalExceptionMappings =
                ([(NoStackUsernameNotFoundException.name): AuthenticationFailureUserNotFoundEvent.name] as Properties)
    }

    userDetailsService(ApplicationGormUserDetailsService) {
        grailsApplication = ref('grailsApplication')
    }
}
