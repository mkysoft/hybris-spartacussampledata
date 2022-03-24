/*
 * Copyright (c) 2019 SAP SE or an SAP affiliate company. All rights reserved.
 */
package de.hybris.platform.spartacussampledata.setup.impl;

import de.hybris.platform.commerceservices.dataimport.impl.CoreDataImportService;
import de.hybris.platform.commerceservices.dataimport.impl.SampleDataImportService;
import de.hybris.platform.commerceservices.setup.AbstractSystemSetup;
import de.hybris.platform.commerceservices.setup.data.ImportData;
import de.hybris.platform.commerceservices.setup.events.SampleDataImportedEvent;
import de.hybris.platform.core.initialization.SystemSetup;
import de.hybris.platform.core.initialization.SystemSetupContext;
import de.hybris.platform.core.initialization.SystemSetupParameter;
import de.hybris.platform.core.initialization.SystemSetupParameterMethod;
import de.hybris.platform.spartacussampledata.constants.SpartacussampledataConstants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Required;


@SystemSetup(extension = SpartacussampledataConstants.EXTENSIONNAME)
public class SpSampleDataSystemSetup extends AbstractSystemSetup
{
    public static final String ELECTRONICS = "electronics";
    public static final String APPAREL_UK = "apparel-uk";
    public static final String POWERTOOLS = "powertools";

    private static final String IMPORT_SAMPLE_DATA = "importSampleData";
    private static final String ACTIVATE_SOLR_CRON_JOBS = "activateSolrCronJobs";

    private CoreDataImportService coreDataImportService;
    private SpaSampleDataImportService sampleDataImportService;

    @SystemSetupParameterMethod
    @Override
    public List<SystemSetupParameter> getInitializationOptions()
    {
        final List<SystemSetupParameter> params = new ArrayList<SystemSetupParameter>();

        params.add(createBooleanSystemSetupParameter(IMPORT_SAMPLE_DATA, "Import Sample Data", true));
        params.add(createBooleanSystemSetupParameter(ACTIVATE_SOLR_CRON_JOBS, "Activate Solr Cron Jobs", true));

        return params;
    }

    /**
     * This method will be called during the system initialization.
     *
     * @param context
     *           the context provides the selected parameters and values
     */
    @SystemSetup(type = SystemSetup.Type.PROJECT, process = SystemSetup.Process.UPDATE)
    public void createProjectData(final SystemSetupContext context)
    {
        final List<ImportData> importData = new ArrayList<ImportData>();

        final ImportData electronicsSpaImportData = new ImportData();
        electronicsSpaImportData.setProductCatalogName(ELECTRONICS);
        electronicsSpaImportData.setContentCatalogNames(Arrays.asList(ELECTRONICS));
        electronicsSpaImportData.setStoreNames(Arrays.asList(ELECTRONICS));
        importData.add(electronicsSpaImportData);

        final ImportData apparelSpaImportData = new ImportData();
        apparelSpaImportData.setProductCatalogName(APPAREL_UK);
        apparelSpaImportData.setContentCatalogNames(Arrays.asList(APPAREL_UK));
        apparelSpaImportData.setStoreNames(Arrays.asList(APPAREL_UK));
        importData.add(apparelSpaImportData);

        final ImportData powertoolsSpaImportData = new ImportData();
        powertoolsSpaImportData.setProductCatalogName(POWERTOOLS);
        powertoolsSpaImportData.setContentCatalogNames(Arrays.asList(POWERTOOLS));
        powertoolsSpaImportData.setStoreNames(Arrays.asList(POWERTOOLS));
        importData.add(powertoolsSpaImportData);

        getSampleDataImportService().importSampleData(SpartacussampledataConstants.EXTENSIONNAME, context, importData, true);
    }

    public CoreDataImportService getCoreDataImportService()
    {
        return coreDataImportService;
    }

    @Required
    public void setCoreDataImportService(final CoreDataImportService coreDataImportService)
    {
        this.coreDataImportService = coreDataImportService;
    }

    public SpaSampleDataImportService getSampleDataImportService()
    {
        return sampleDataImportService;
    }

    @Required
    public void setSampleDataImportService(final SpaSampleDataImportService sampleDataImportService)
    {
        this.sampleDataImportService = sampleDataImportService;
    }
}
