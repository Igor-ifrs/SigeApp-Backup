package beans;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PreDestroy;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import modelo.Evento;
import modelo.Usuario;
import org.hibernate.mapping.Array;
import persistencia.EventoDAO;

@ManagedBean(name = "EventoBean")
@RequestScoped
public class CadastroEventosBean {

    private Evento evento = new Evento();
    private EventoDAO dao = new EventoDAO();
    private List<Evento> listaEventos;
    private List<Evento> listaEventosUsuarios = new ArrayList<>();
    private List<Usuario> lista2EventosUsuarios = new ArrayList<>();

    public CadastroEventosBean() {
        listaEventos = dao.listar();
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public List<Evento> getListaEventos() {
        return listaEventos;
    }

    public void adicionar(Evento evento,Usuario usuario){
        evento.getEvento().add(usuario);
        //lista2EventosUsuarios.add(usuario);
        //evento.setEvento(lista2EventosUsuarios);
        
        dao.salvar(evento);
        enviarMensagem(FacesMessage.SEVERITY_INFO, "Sua inscrição foi realizada com sucesso no(a)  "+evento.getTipoEvento()+" : "+evento.getTituloEvento()+" .");
        evento = new Evento();
        listaEventos = dao.listar();
    }
    
    
    public void salvar() {
        dao.salvar(evento);
        enviarMensagem(FacesMessage.SEVERITY_INFO, "Evento cadastrado com sucesso");
        evento = new Evento();
        listaEventos = dao.listar();
    }

    public void carregar(int id) {
        evento = dao.carregar(id);
    }

    public void remover(Evento evento) {
        dao.remover(evento);
        enviarMensagem(FacesMessage.SEVERITY_INFO, "Evento removido com sucesso");
        listaEventos = dao.listar();
    }

    private void enviarMensagem(Severity sev, String msg) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(sev, msg, ""));
    }

    @PreDestroy
    public void encerrar() {
        dao.encerrar();
    }

    public List<Evento> getListaEventosUsuarios() {
        return listaEventosUsuarios;
    }

    public void setListaEventosUsuarios(List<Evento> listaEventosUsuarios) {
        this.listaEventosUsuarios = listaEventosUsuarios;
    }
}