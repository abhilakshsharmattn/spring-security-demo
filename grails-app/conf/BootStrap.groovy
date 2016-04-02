import com.demo.Role
import com.demo.User
import com.demo.UserRole

class BootStrap {

    def init = { servletContext ->

        User adminUser = new User("admin_user", "asdf1234").save(flush: true)
        Role adminRole = new Role("ROLE_ADMIN").save(flush: true)
        UserRole.create(adminUser, adminRole, true)

        User passwordExpiredUser = new User("pass_exp_user", "asdf1234")
        passwordExpiredUser.passwordExpired = true
        passwordExpiredUser.save(flush: true)
    }
    def destroy = {
    }
}
