/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the Elastic License;
 * you may not use this file except in compliance with the Elastic License.
 */
package org.elasticsearch.xpack.vectors;

import org.elasticsearch.action.ActionListener;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.license.XPackLicenseState;
import org.elasticsearch.xpack.core.XPackFeatureSet;
import org.elasticsearch.xpack.core.XPackField;
import org.elasticsearch.xpack.core.XPackSettings;
import org.elasticsearch.xpack.core.vectors.VectorsFeatureSetUsage;

import java.util.Map;

public class VectorsFeatureSet implements XPackFeatureSet {

    private final boolean enabled;
    private final XPackLicenseState licenseState;

    @Inject
    public VectorsFeatureSet(Settings settings, XPackLicenseState licenseState) {
        this.enabled = XPackSettings.VECTORS_ENABLED.get(settings);
        this.licenseState = licenseState;
    }

    @Override
    public String name() {
        return XPackField.VECTORS;
    }

    @Override
    public boolean available() {
        return licenseState != null && licenseState.isVectorsAllowed();
    }

    @Override
    public boolean enabled() {
        return enabled;
    }

    @Override
    public Map<String, Object> nativeCodeInfo() {
        return null;
    }

    @Override
    public void usage(ActionListener<XPackFeatureSet.Usage> listener) {
        listener.onResponse(new VectorsFeatureSetUsage(available(), enabled()));
    }
}
