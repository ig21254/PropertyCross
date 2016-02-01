package com.lasalle.second.part.propertycross.repositories;

import com.lasalle.second.part.propertycross.model.Property;

import java.util.List;

/**
 * Created by Eduard on 24/01/2016.
 */
public interface FavoritesRepo {

    public List<Property> getFavoriteProperties();
    public boolean addFavorite (Property property);

}
