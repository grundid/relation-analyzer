/**
 * This file is part of Relation Analyzer for OSM.
 * Copyright (c) 2001 by Adrian Stabiszewski, as@grundid.de
 * <p>
 * Relation Analyzer is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * Relation Analyzer is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 * <p>
 * You should have received a copy of the GNU Affero General Public License
 * along with Relation Analyzer.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.osmsurround.ra.report;

import org.osmsurround.ra.stats.RelationStatistics;

import java.util.List;

public class Report {

    private boolean validRelation;
    private boolean gone;
    private RelationRating relationRating;
    private RelationInfo relationInfo;
    private List<ReportItem> reportItems;
    private RelationStatistics highwayStatistics;
    private RelationStatistics surfaceStatistics;
    private String elevationProfileJson;

    public Report() {
    }

    public Report(boolean gone) {
        this.gone = gone;
    }

    public String getElevationProfileJson() {
        return elevationProfileJson;
    }

    public void setElevationProfileJson(String elevationProfileJson) {
        this.elevationProfileJson = elevationProfileJson;
    }

    public RelationRating getRelationRating() {
        return relationRating;
    }

    public void setRelationRating(RelationRating relationRating) {
        this.relationRating = relationRating;
    }

    public RelationInfo getRelationInfo() {
        return relationInfo;
    }

    public void setRelationInfo(RelationInfo relationInfo) {
        this.relationInfo = relationInfo;
    }

    public List<ReportItem> getReportItems() {
        return reportItems;
    }

    public void setReportItems(List<ReportItem> reportItems) {
        this.reportItems = reportItems;
    }

    public RelationStatistics getHighwayStatistics() {
        return highwayStatistics;
    }

    public void setHighwayStatistics(RelationStatistics highwayStatistics) {
        this.highwayStatistics = highwayStatistics;
    }

    public RelationStatistics getSurfaceStatistics() {
        return surfaceStatistics;
    }

    public void setSurfaceStatistics(RelationStatistics surfaceStatistics) {
        this.surfaceStatistics = surfaceStatistics;
    }

    public boolean isValidRelation() {
        return validRelation;
    }

    public void setValidRelation(boolean validRelation) {
        this.validRelation = validRelation;
    }

    public boolean isGone() {
        return gone;
    }

    public boolean isValidForExport() {
        return getReportItems().size() == 1 && getReportItems().get(0).getEndNodeDistances().size() == 1;
    }
}
