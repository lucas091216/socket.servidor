package server;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author vital
 */
class Mensagem implements Serializable{
    
    private String operacao;
    private Status status;
    
    Map<String, Object> params;
    
    public Mensagem(String operacao){
        this.operacao = operacao;
        params = new HashMap<>();
       
    }
    
    public String getOperacao(){
        return operacao;
    }
    
    public void setStatus(Status s){
        this.status = s;
        
    }
    public Status getStatus(){
        return status; 
    }
    
    public void setParam(String chave, Object valor){
        params.put(chave, valor);
    }
    
    public Object getParam(String chave){
        return params.get(chave);
    }

    @Override
    public String toString() {
        String m = "Operacao: " + operacao;
        m += "\nStatus: "+status;
        
        m += "\nParâmetros:\n ";
        for (String p: params.keySet()) {
            m += "\n"+p+" : "+params.get(p);
        }
        
        return m;
    }
    
    
    
    
}
