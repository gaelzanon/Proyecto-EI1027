package es.uji.ei1027.clubesportiu.model;
public class TemporalService {
    private String code ;
    private String startTime ;
    private String endTime;

    public TemporalService() {}
    
    public String getCode() {
        return code ;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStartTime() {
        return startTime ;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime ;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }




    @Override
    public String toString() {
        return "temporalService{" +"code='" + code + " \' " +
		", startTime='" + startTime + " \' " +
		", endTime=" + endTime +
		"}" ;
	}
}