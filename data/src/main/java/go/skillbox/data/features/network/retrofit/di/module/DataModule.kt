package go.skillbox.data.features.network.retrofit.di.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import go.skillbox.data.features.network.retrofit.FeaturesDataSource
import go.skillbox.data.features.network.retrofit.FeaturesNetworkLoader
import go.skillbox.data.repositories.FeaturesRepositoryImpl
import go.skillbox.data.repositories.NetworkFeaturesRepository
import go.skillbox.domain.repositories.FeaturesRepository
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    companion object {
        private const val BASE_URL = "https://api.opentripmap.com"
    }

    @Provides
    fun provideFeaturesRepository(
        networkFeaturesRepository: NetworkFeaturesRepository
    ): FeaturesRepository = FeaturesRepositoryImpl(networkFeaturesRepository)

    @Provides
    fun provideNetworkFeaturesRepository(
        apiService: FeaturesNetworkLoader
    ): NetworkFeaturesRepository = FeaturesDataSource(apiService)

    @Provides
    fun provideFeaturesNetworkLoader(): FeaturesNetworkLoader {
        val retrofit =
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
        return retrofit.create(FeaturesNetworkLoader::class.java)
            ?: throw RuntimeException("Api service is null")
    }
}