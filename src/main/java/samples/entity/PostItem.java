package samples.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import samples.enums.PostItemType;
import samples.enums.Status;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "POST_ITEM")
@Entity
public class PostItem implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @Column(name = "RECEIVER_PHONE")
    private String receiverPhone;

    @Column(name = "SENDER_PHONE")
    private String senderPhone;

    @Column(name = "LABEL")
    private String label;

    @Column(name = "TRACKING_CODE")
    private String trackingCode;

    @Column(name = "TYPE")
    @Enumerated
    private PostItemType postItemType;

    @Column(name = "RECEIVE_DATE")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date receiveDate;

    @Column(name = "DELIVER_DATE")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date deliverDate;

    private Status status;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReceiverPhone() {
        return receiverPhone;
    }

    public void setReceiverPhone(String receiverPhone) {
        this.receiverPhone = receiverPhone;
    }

    public String getSenderPhone() {
        return senderPhone;
    }

    public void setSenderPhone(String senderPhone) {
        this.senderPhone = senderPhone;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public PostItemType getPostItemType() {
        return postItemType;
    }

    public void setPostItemType(PostItemType postItemType) {
        this.postItemType = postItemType;
    }

    public String getTrackingCode() {
        return trackingCode;
    }

    public void setTrackingCode(String trackingCode) {
        this.trackingCode = trackingCode;
    }

    public Date getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(Date receiveDate) {
        this.receiveDate = receiveDate;
    }

    public Date getDeliverDate() {
        return deliverDate;
    }

    public void setDeliverDate(Date deliverDate) {
        this.deliverDate = deliverDate;
    }

    public Status getStats() {
        return status;
    }

    public void setStats(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "PostItem{" +
                "id=" + id +
                ", receiverPhone='" + receiverPhone + '\'' +
                ", senderPhone='" + senderPhone + '\'' +
                ", label='" + label + '\'' +
                ", trackingCode='" + trackingCode + '\'' +
                ", postItemType=" + postItemType +
                '}';
    }
}