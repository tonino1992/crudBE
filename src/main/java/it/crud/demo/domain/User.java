package it.crud.demo.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import it.crud.demo.domain.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User implements UserDetails{

	@Id
	@Column(nullable = false)
	private String userId;
	@Column(nullable = false)
	private String email;
	@Column(nullable = false)
	private String password;
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private UserRole role;

	@OneToOne(mappedBy = "userId")
	@JsonManagedReference
	private Teacher teacher;

	@OneToOne(mappedBy = "userId")
	@JsonManagedReference
	private Student student;

	@OneToMany(mappedBy = "userId")
	@JsonManagedReference
	private List<ResetPasswordToken> tokens;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
	    List<GrantedAuthority> authorities = new ArrayList<>();
	    if (this.role == UserRole.STUDENT) {
	        authorities.add(new SimpleGrantedAuthority("ROLE_STUDENT"));
	    } else if (this.role == UserRole.TEACHER) {
	        authorities.add(new SimpleGrantedAuthority("ROLE_TEACHER"));
	    }
	    return authorities;
	}
	
	@Override
	public String getPassword() {
	    return this.password;
	}

	@Override
	public String getUsername() {
	    return this.userId;
	}

	@Override
	public boolean isAccountNonExpired() {
	    return true;
	}

	@Override
	public boolean isAccountNonLocked() {
	    return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
	    return true;
	}

	@Override
	public boolean isEnabled() {
	    return true;
	}

}
