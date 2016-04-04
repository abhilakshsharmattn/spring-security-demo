import com.demo.Role
import com.demo.User
import com.demo.UserRole

class BootStrap {

    def init = { servletContext ->

        Role adminRole = new Role("ROLE_ADMIN").save(flush: true)
        Role superUserRole = new Role("ROLE_SUPER_USER").save(flush: true)

        User adminUser = new User("admin_user", "asdf1234").save(flush: true)
        UserRole.create(adminUser, adminRole, true)

        User passwordExpiredUser = new User("pass_exp_user", "asdf1234")
        passwordExpiredUser.passwordExpired = true
        passwordExpiredUser.save(flush: true)

        User superUser = new User("super_user", "asdf1234").save(flush: true)
        UserRole.create(superUser, superUserRole, true)


        Role customerUserRole = new Role("ROLE_CUSTOMER_USER").customer.save(flush: true)
        User customerUser = new User("customer_user", "asdf1234").customer.save(flush: true)
        new UserRole(user: customerUser, role: customerUserRole).customer.save(flush: true)
    }
    def destroy = {
    }
}
