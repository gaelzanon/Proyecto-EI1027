package es.uji.ei1027.clubesportiu.model;
public class PermanentService {
    private String code ;

    public PermanentService() {}
    
    public String getCode() {
        return code ;
    }

    public void setCode(String code) {
        this.code = code;
    }



    @Override
    public String toString() {
        return "permanentService{" +"code='" + code + " \' }" ;
	}
}