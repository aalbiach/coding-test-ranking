package com.idealista.application.converter;

import com.idealista.application.service.picture.PictureService;
import com.idealista.application.service.publicAd.PublicAdResponse;
import com.idealista.domain.ad.Ad;
import com.idealista.domain.ad.AdTypology;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AdToPublicAdResponseConverterTest {

    @Mock
    private PictureService pictureService;

    @InjectMocks
    private AdToPublicAdResponseConverter adToPublicAdResponseConverter;

    @Test
    public void shouldConvertAdToPublicAdResponse() {
        final Ad ad = new Ad();
        ad.setId("1");
        ad.setTypology(AdTypology.CHALET);
        ad.setDescription("Description");
        ad.setPictures(Arrays.asList("1", "2"));
        ad.setHouseSize(100);
        ad.setGardenSize(200);

        final PublicAdResponse expected = new PublicAdResponse();
        expected.setId("1");
        expected.setTypology("CHALET");
        expected.setDescription("Description");
        expected.setPictureUrls(Arrays.asList("http://www.idealista.com/pictures/1", "http://www.idealista.com/pictures/2"));
        expected.setHouseSize(100);
        expected.setGardenSize(200);

        when(pictureService.getPictureUrlsByIds(anyList()))
                .thenReturn(Arrays.asList("http://www.idealista.com/pictures/1", "http://www.idealista.com/pictures/2"));

        assertThat(adToPublicAdResponseConverter.convert(ad)).isEqualTo(expected);

        verify(pictureService, only()).getPictureUrlsByIds(anyList());
    }

    @Test
    public void shouldConvertAdToPublicAdResponseWithEmptyPicturesUrlList() {
        final Ad ad = new Ad();
        ad.setId("1");
        ad.setTypology(AdTypology.CHALET);
        ad.setDescription("Description");
        ad.setPictures(Collections.emptyList());
        ad.setHouseSize(100);
        ad.setGardenSize(200);

        final PublicAdResponse expected = new PublicAdResponse();
        expected.setId("1");
        expected.setTypology("CHALET");
        expected.setDescription("Description");
        expected.setPictureUrls(Collections.emptyList());
        expected.setHouseSize(100);
        expected.setGardenSize(200);

        when(pictureService.getPictureUrlsByIds(anyList()))
                .thenReturn(Collections.emptyList());

        assertThat(adToPublicAdResponseConverter.convert(ad)).isEqualTo(expected);

        verify(pictureService, only()).getPictureUrlsByIds(anyList());
    }
}
