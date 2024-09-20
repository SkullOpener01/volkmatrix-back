package com.volkmatrix.website.service.model;

import com.volkmatrix.common.model.BaseModel;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class DemoRequests extends BaseModel {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(name = "name", nullable = false) // Maps to the "username" column
  private String name;
  @Column(name = "email", nullable = false) // Maps to the "email" column
  private String email;
  @Column(name = "mobile", nullable = false)
  private String mobile;
  @Column(name = "message", nullable = false)
  private String message;
@Column(name = "demoDateTime", nullable = false)
  private String demoDateTime;
  private boolean wapNotification;    // this is for getting opt-in for whatsapp notification
  private boolean connectExpert;  // this is by default true, if user wants our agent to connect with them
  private boolean keepInformed;   // send mail to customers about products and new offers

  //  ----------------------------------
//For our management of customer
  private boolean isContacted;
  private String byWhom;
  private String remarks;
  private boolean isProposalSent;
  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id") // Foreign key column in the services table
  private List<VolkServices> volkServices; // List of services associated with the user


}
