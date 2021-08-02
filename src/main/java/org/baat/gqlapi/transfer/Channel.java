package org.baat.gqlapi.transfer;

import java.util.Objects;

public class Channel {

    private Long id;
    private String name;
    private String description;
    private Boolean archived;

    public Channel() {
    }

    public Channel(Long id, String name, String description, Boolean archived) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.archived = archived;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getArchived() {
        return archived;
    }

    public void setArchived(Boolean archived) {
        this.archived = archived;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Channel channel = (Channel) o;
        return Objects.equals(id, channel.id) && Objects.equals(name, channel.name) && Objects.equals(description, channel.description) && Objects.equals(archived, channel.archived);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, archived);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Channel{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", archived=").append(archived);
        sb.append('}');
        return sb.toString();
    }
}
