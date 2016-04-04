package com.demo.security.service

import com.demo.Role
import com.demo.User
import com.demo.UserRole
import grails.plugin.springsecurity.SpringSecurityUtils
import grails.plugin.springsecurity.userdetails.GormUserDetailsService
import grails.plugin.springsecurity.userdetails.NoStackUsernameNotFoundException
import org.codehaus.groovy.grails.commons.GrailsApplication
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException

class ApplicationGormUserDetailsService extends GormUserDetailsService {
    protected Logger log = LoggerFactory.getLogger(getClass())

    /**
     * Some Spring Security classes (e.g. RoleHierarchyVoter) expect at least one role, so
     * we give a user with no granted roles this one which gets past that restriction but
     * doesn't grant anything.
     */
    static final GrantedAuthority NO_ROLE = new SimpleGrantedAuthority(SpringSecurityUtils.NO_ROLE)

    /** Dependency injection for the application. */
    GrailsApplication grailsApplication

    UserDetails loadUserByUsername(String username, boolean loadRoles) throws UsernameNotFoundException {

        def conf = SpringSecurityUtils.securityConfig

        if(username.contains("customer")) {
            User.customer.withNewSession { status ->
                User user = User.customer.findWhere((conf.userLookup.usernamePropertyName): username)
                if (!user) {
                    log.warn "User not found: $username"
                    throw new NoStackUsernameNotFoundException()
                }

                Collection<GrantedAuthority> authorities = loadAuthorities(user, username, loadRoles)
                createUserDetails user, authorities
            }
        } else {
            super.setGrailsApplication(grailsApplication)
            super.loadUserByUsername(username, loadRoles)
        }
    }

    protected Collection<GrantedAuthority> loadAuthorities(user, String username, boolean loadRoles) {
        if (!loadRoles) {
            return [NO_ROLE]
        }
        def authorities
        if(username.contains("customer")) {
            UserRole.withTransaction { status ->
                Collection<Role> userAuthorities = UserRole.customer.findAllByUser(user)*.role
                authorities = userAuthorities.collect { new SimpleGrantedAuthority(it.authority) }
            }
        } else {
            authorities = super.loadAuthorities(user, username, loadRoles)
        }
        authorities ?: [NO_ROLE]
    }
}
