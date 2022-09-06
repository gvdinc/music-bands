package database;

import collections.MusicBand;

public class SqlFormatter {
    public static String buildAppendRequest(MusicBand band) {
        StringBuilder params = new StringBuilder();
        StringBuilder values = new StringBuilder();
        // template making
        params.append("insert into ").append(URLS.BANDS.getPass()).append(" (id, creation_date, ");
        values.append("VALUES (DEFAULT, DEFAULT, ");

        if (band.getName() != null)                 {params.append("name, ");                       values.append("'").append(band.getName()).append("', "); }  else {return null;}
        if (band.getCoordinates().isCorrect())      {params.append("coordinate_x, coordinate_y, "); values.append(band.getCoordinates().getX()).append(", ").append(band.getCoordinates().getY()).append(", ");}  else {return null;}
        if (band.getGenre() != null)                {params.append("genre, ");                      values.append("'").append(band.getGenre()).append("', ");}  else {return null;}
        if (band.getUsername() != null)             {params.append("username, ");                   values.append("'").append(band.getUsername()).append("', ");}  else {return null;}

        if (band.getNumberOfParticipants() != null) {params.append("number_of_participants, ");     values.append(band.getNumberOfParticipants()).append(", ");}
        if (band.getSinglesCount() != null)         {params.append("singles_count, ");              values.append(band.getSinglesCount()).append(", ");}
        if (band.getBestAlbum() != null){
            if (band.getBestAlbum().getName() != null)  {params.append("best_album_name, ");        values.append("'").append(band.getBestAlbum().getName()).append("', ");}
            if (band.getBestAlbum().getSales() > 0)     {params.append("best_album_sales, ");       values.append(band.getBestAlbum().getSales()).append(", ");}
        }

        params.delete(params.length()-2, params.length()-1); values.delete(values.length()-2, values.length()-1);
        params.append(") "); values.append(") ");
        String request = params + values.toString();
        //System.out.println(request);
        return request;
    }

    public static String buildUpdateRequest(MusicBand band){
        StringBuilder builder = new StringBuilder();
        builder.append("update ").append(URLS.BANDS.getPass()).append(" set ");
        if (band.getName() != null)                 {builder.append("name = ");                       builder.append("'").append(band.getName()).append("', "); }  else {return null;}
        if (band.getCoordinates().isCorrect())      {builder.append("coordinate_x = ").append(band.getCoordinates().getX()).append(", coordinate_y = ").append(band.getCoordinates().getY()).append(", ");}  else {return null;}
        if (band.getGenre() != null)                {builder.append("genre = ");                      builder.append("'").append(band.getGenre()).append("', ");}  else {return null;}

        if (band.getNumberOfParticipants() != null) {builder.append("number_of_participants = ");     builder.append(band.getNumberOfParticipants()).append(", ");}
        if (band.getSinglesCount() != null)         {builder.append("singles_count = ");              builder.append(band.getSinglesCount()).append(", ");}
        if (band.getBestAlbum() != null){
            if (band.getBestAlbum().getName() != null)  {builder.append("best_album_name = ");        builder.append("'").append(band.getBestAlbum().getName()).append("', ");}
            if (band.getBestAlbum().getSales() > 0)     {builder.append("best_album_sales = ");       builder.append(band.getBestAlbum().getSales()).append(", ");}
        }
        builder.delete(builder.length()-2, builder.length()-1);
        if (band.getUsername() != null)             {builder.append("where username = ");             builder.append("'").append(band.getUsername()).append("' and ");}  else {return null;}
        if (band.getId() >= 0)                      {builder.append("id = ").append(band.getId());}
        //System.out.println(builder);
        return builder.toString();
    }
}
