package mx.com.oposicion.model;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class Usuario implements UserDetails {

    /**
     *
     */
    private static final long serialVersionUID = 8547828903235930641L;
    private int idUsuario;
    private String usuario;
    private String clave;
    private String correo;
    private String nombre;
    private String apaterno;
    private String amaterno;
    private Date fechaCreacion;
    private boolean cuentaNoExpirada;
    private boolean cuentaNoBloqueada;
    private boolean credencialNoExpirada;
    private boolean habilitado;
    private int contadorIntentosFallidos;
    private Date instanteDeBloqueo;
    private String preguntaSecreta;
    private String respuestaSecreta;
    private String idSeguridad;
    private Date ventanaParaIdSeguridad;
    private boolean activo;
    private List<GrantedAuthority> perfiles;
    private String sessionId;
    private Date sessionLastRequest;
    private boolean sessionExpired;

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return this.perfiles;
    }

    public void setAuthorities(List<GrantedAuthority> authorities) {
        this.perfiles = authorities;
    }

    @Override
    public String getPassword() {
        return this.clave;
    }

    public void setPassword(String password) {
        this.clave = password;
    }

    @Override
    public String getUsername() {
        return this.usuario;
    }

    public void setUsername(String username) {
        this.usuario = username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.cuentaNoExpirada;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        this.cuentaNoExpirada = accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.cuentaNoBloqueada;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.cuentaNoBloqueada = accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credencialNoExpirada;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credencialNoExpirada = credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.habilitado;
    }

    public void setEnabled(boolean enabled) {
        this.habilitado = enabled;
    }

    /**
     * ***************************   **********************************
     */
    public List<GrantedAuthority> getPerfiles() {
        return perfiles;
    }

    public void setPerfiles(List<GrantedAuthority> perfiles) {
        this.perfiles = perfiles;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApaterno() {
        return apaterno;
    }

    public void setApaterno(String apaterno) {
        this.apaterno = apaterno;
    }

    public String getAmaterno() {
        return amaterno;
    }

    public void setAmaterno(String amaterno) {
        this.amaterno = amaterno;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public boolean isCuentaNoExpirada() {
        return cuentaNoExpirada;
    }

    public void setCuentaNoExpirada(boolean cuentaNoExpirada) {
        this.cuentaNoExpirada = cuentaNoExpirada;
    }

    public boolean isCuentaNoBloqueada() {
        return cuentaNoBloqueada;
    }

    public void setCuentaNoBloqueada(boolean cuentaNoBloqueada) {
        this.cuentaNoBloqueada = cuentaNoBloqueada;
    }

    public boolean isCredencialNoExpirada() {
        return credencialNoExpirada;
    }

    public void setCredencialNoExpirada(boolean credencialNoExpirada) {
        this.credencialNoExpirada = credencialNoExpirada;
    }

    public boolean isHabilitado() {
        return habilitado;
    }

    public void setHabilitado(boolean habilitado) {
        this.habilitado = habilitado;
    }

    public int getContadorIntentosFallidos() {
        return contadorIntentosFallidos;
    }

    public void setContadorIntentosFallidos(int contadorIntentosFallidos) {
        this.contadorIntentosFallidos = contadorIntentosFallidos;
    }

    public Date getInstanteDeBloqueo() {
        return instanteDeBloqueo;
    }

    public void setInstanteDeBloqueo(Date instanteDeBloqueo) {
        this.instanteDeBloqueo = instanteDeBloqueo;
    }

    public String getPreguntaSecreta() {
        return preguntaSecreta;
    }

    public void setPreguntaSecreta(String preguntaSecreta) {
        this.preguntaSecreta = preguntaSecreta;
    }

    public String getRespuestaSecreta() {
        return respuestaSecreta;
    }

    public void setRespuestaSecreta(String respuestaSecreta) {
        this.respuestaSecreta = respuestaSecreta;
    }

    public String getIdSeguridad() {
        return idSeguridad;
    }

    public void setIdSeguridad(String idSeguridad) {
        this.idSeguridad = idSeguridad;
    }

    public Date getVentanaParaIdSeguridad() {
        return ventanaParaIdSeguridad;
    }

    public void setVentanaParaIdSeguridad(Date ventanaParaIdSeguridad) {
        this.ventanaParaIdSeguridad = ventanaParaIdSeguridad;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public Date getSessionLastRequest() {
        return sessionLastRequest;
    }

    public void setSessionLastRequest(Date sessionLastRequest) {
        this.sessionLastRequest = sessionLastRequest;
    }

    public boolean isSessionExpired() {
        return sessionExpired;
    }

    public void setSessionExpired(boolean sessionExpired) {
        this.sessionExpired = sessionExpired;
    }

    
    
    
}
