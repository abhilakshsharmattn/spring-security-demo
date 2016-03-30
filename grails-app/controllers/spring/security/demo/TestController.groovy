package spring.security.demo

import grails.plugin.springsecurity.annotation.Secured

class TestController {

    @Secured(['ROLE_ADMIN'])
    def index() {
        render("This action is secured for role ROLE_ADMIN.")
    }
}
