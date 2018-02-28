package com.kgisl.eventhandler.eventhandler;

import javax.persistence.*;

@Entity
public class Agenda {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long agendaId;

	@Column
	private String time;

	@Column
	private String description;

	@Column
	private String instructor;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "eventId", updatable = false, insertable = false)
	private Event event;

	public Agenda() {
		super();
		// TODO Auto-generated constructor stub
	}

	

	public Long getaId() {
		return agendaId;
	}

	public void setaId(Long agendaId) {
		this.agendaId = agendaId;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getInstructor() {
		return instructor;
	}

	public void setInstructor(String instructor) {
		this.instructor =instructor;
	}

	public void setEvent(Event event) {
		this.event = event;}
	}