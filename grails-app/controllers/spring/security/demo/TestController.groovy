package spring.security.demo

import grails.plugin.springsecurity.annotation.Secured

class TestController {

    @Secured(['ROLE_ADMIN'])
    def index() {
        render("This action is secured for role <b>ROLE_ADMIN</b>.")
    }

    @Secured(['ROLE_SUPER_USER'])
    def superUser() {
        render("This action is secured for role <b>ROLE_SUPER_USER</b>.")
    }

    @Secured(['ROLE_CUSTOMER_USER'])
    def customerUser() {
        render("This action is secured for role <b>ROLE_CUSTOMER_USER</b>.")
    }

    @Secured("permitAll")
    def passwordExpired() {
        render("Your credentials have expired!!!!")
    }
}
