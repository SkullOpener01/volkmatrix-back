package com.volkmatrix.whatsapp.service.dto;

import lombok.Data;

import java.util.List;

@Data
public class WapWebhookPayload {

  private String object;
  private List<Entry> entry;

  @Data
  public static class Entry {
    private String id;
    private List<Change> changes;
  }

  @Data
  public static class Change {
    private Value value;
    private String field;
  }

  @Data
  public static class Value {
    private String messaging_product;
    private Metadata metadata;
    private List<Contact> contacts;
    private List<Message> messages;
    private List<Status> statuses;
  }

  @Data
  public static class Metadata {
    private String display_phone_number;
    private String phone_number_id;
  }

  @Data
  public static class Contact {
    private Profile profile;
    private String wa_id;
  }

  @Data
  public static class Profile {
    private String name;
  }

  @Data
  public static class Message {
    private String from;
    private String id;
    private long timestamp;
    private String type;
    private Text text;
    private Reaction reaction;
    private Image image;
    private Video video;
    private Document document;
    private Audio audio;
    private Sticker sticker;
    private Location location;
    private List<ContactDetail> contacts;
    private Context context;
    private Interactive interactive;
    private List<Error> errors;
  }

  @Data
  public static class Text {
    private String body;
  }

  @Data
  public static class Reaction {
    private String message_id;
    private String emoji;
  }

  @Data
  public static class Image {
    private String caption;
    private String mime_type;
    private String sha256;
    private String id;
  }
  @Data
  public static class Video {
    private String mime_type;
    private String sha256;
    private String id;
  }
  @Data
  public static class Audio {
    private boolean audio;
    private String mime_type;
    private String sha256;
    private String id;
  }
  @Data
  public static class Document {
    private String fileName;
    private String mime_type;
    private String sha256;
    private String id;
  }

  @Data
  public static class Sticker {
    private String mime_type;
    private String sha256;
    private String id;
  }

  @Data
  public static class Location {
    private double latitude;
    private double longitude;
    private String name;
    private String address;
  }

  @Data
  public static class ContactDetail {
    private List<Address> addresses;
    private String birthday;
    private List<Email> emails;
    private Name name;
    private Org org;
    private List<Phone> phones;
    private List<Url> urls;
  }

  @Data
  public static class Address {
    private String city;
    private String country;
    private String country_code;
    private String state;
    private String street;
    private String type;
    private String zip;
  }

  @Data
  public static class Email {
    private String email;
    private String type;
  }

  @Data
  public static class Name {
    private String formatted_name;
    private String first_name;
    private String last_name;
    private String middle_name;
    private String suffix;
    private String prefix;
  }

  @Data
  public static class Org {
    private String company;
    private String department;
    private String title;
  }

  @Data
  public static class Phone {
    private String phone;
    private String wa_id;
    private String type;
  }

  @Data
  public static class Url {
    private String url;
    private String type;
  }

  @Data
  public static class Context {
    private String from;
    private String id;
  }

  @Data
  public static class Interactive {
    private ButtonReply button_reply;
    private ListReply list_reply;
    private String type;
  }

  @Data
  public static class ButtonReply {
    private String id;
    private String title;
  }

  @Data
  public static class ListReply {
    private String id;
    private String title;
    private String description;
  }

  @Data
  public static class Error {
    private int code;
    private String details;
    private String title;
  }

  @Data
  public static class Status {
    private String id;
    private String status;
    private long timestamp;
    private String recipient_id;
    private Conversation conversation;
    private Pricing pricing;
    private List<Errors> errors;
  }

  @Data
  public static class Conversation {
    private String id;
    private String expiration_timestamp;
    private Origin origin;
  }

  @Data
  public static class Origin {
    private String type;
  }

  @Data
  public static class Pricing {
    private boolean billable;
    private String pricing_model;
    private String category;
  }

  @Data
  public static class Errors{
    private int code;
    private String title;
  }

}


