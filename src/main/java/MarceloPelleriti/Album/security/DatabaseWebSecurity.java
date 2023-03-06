package MarceloPelleriti.Album.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class DatabaseWebSecurity extends WebSecurityConfigurerAdapter {
	@Autowired
	private DataSource dataSource;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(dataSource)
		.usersByUsernameQuery("select username, password, estatus from usuarios where username=?")
		.authoritiesByUsernameQuery("select u.username, p.perfil from usuarioperfil up " + 
		"inner join usuarios u on u.id = up.idUsuario " + 
		"inner join perfiles p on p.id = up.idPerfil " + 
		"where u.username = ?");
	
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		// Recursos estaticos no requieren autenticacion
		.antMatchers(
			"/bootstrap/**",
			"/images/**",
			"/tinymce/**").permitAll()
		// Vistas publicas no requieren autenticacion
		.antMatchers(
			"/",
			"/signup",
			"/search",
			"/figuritas/view/**").permitAll()
		
		// Asigna permisos a URLs por roles
		.antMatchers("/figuritas/**").hasAnyAuthority("SUPERVISOR", "ADMINISTRADOR")
		.antMatchers("/equipos/**").hasAnyAuthority("SUPERVISOR", "ADMINISTRADOR")
		.antMatchers("/usuarios/**").hasAnyAuthority("ADMINISTRADOR")
		
		// Todas las demas URL requieren autenticacion
		.anyRequest().authenticated()
		// Formulario de login no requiere autenticacion
		.and().formLogin().loginPage("/login").permitAll();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
}
