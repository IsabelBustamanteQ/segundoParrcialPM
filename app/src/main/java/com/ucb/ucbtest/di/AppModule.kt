package com.ucb.ucbtest.di

import android.content.Context
import com.ucb.data.BookRepository
import com.ucb.data.FinanzasRepository
import com.ucb.data.GithubRepository
import com.ucb.data.LoginRepository
import com.ucb.data.MovieRepository
import com.ucb.data.PushNotificationRepository
import com.ucb.data.books.IBookLocalDataSource
import com.ucb.data.books.IBookRemoteDataSource
import com.ucb.data.datastore.ILoginDataStore
import com.ucb.data.git.IGitRemoteDataSource
import com.ucb.data.git.ILocalDataSource
import com.ucb.data.movie.IMovieRemoteDataSource
import com.ucb.data.push.IPushDataSource
import com.ucb.data.simulacro.IFinanzasLocalDataSource
import com.ucb.framework.books.BookLocalDataSource
import com.ucb.framework.books.BookRemoteDataSource
import com.ucb.framework.github.GithubLocalDataSource
import com.ucb.framework.github.GithubRemoteDataSource
import com.ucb.framework.movie.MovieRemoteDataSource
import com.ucb.framework.service.RetrofitBuilder
import com.ucb.ucbtest.R
import com.ucb.usecases.DoLogin
import com.ucb.usecases.FindGitAlias
import com.ucb.usecases.GetPopularMovies
import com.ucb.usecases.SaveGitalias
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import com.ucb.framework.datastore.LoginDataSource
import com.ucb.framework.push.FirebaseNotificationDataSource
import com.ucb.framework.simulacro.FinanzasLocalDataSource
import com.ucb.usecases.GetEmailKey
import com.ucb.usecases.ObtainToken
import com.ucb.usecases.books.FindBooks
import com.ucb.usecases.books.SaveBook
import com.ucb.usecases.simulacro.DeleteFinanza
import com.ucb.usecases.simulacro.DoEgreso
import com.ucb.usecases.simulacro.DoIngreso
import com.ucb.usecases.simulacro.GetRegistros
import com.ucb.usecases.simulacro.GetTotal

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providerRetrofitBuilder(@ApplicationContext context: Context) : RetrofitBuilder {
        return RetrofitBuilder(context)
    }


    @Provides
    @Singleton
    fun gitRemoteDataSource(retrofiService: RetrofitBuilder): IGitRemoteDataSource {
        return GithubRemoteDataSource(retrofiService)
    }

    @Provides
    @Singleton
    fun provideLocalDataSource(@ApplicationContext context: Context): ILocalDataSource {
        return GithubLocalDataSource(context)
    }

    @Provides
    @Singleton
    fun gitRepository(remoteDataSource: IGitRemoteDataSource, localDataSource: ILocalDataSource): GithubRepository {
        return GithubRepository(remoteDataSource, localDataSource)
    }

    @Provides
    @Singleton
    fun provideSaveGitAlias(repository: GithubRepository): SaveGitalias {
        return SaveGitalias(repository)
    }

    @Provides
    @Singleton
    fun provideGitUseCases(githubRepository: GithubRepository): FindGitAlias {
        return FindGitAlias(githubRepository)
    }

    @Provides
    @Singleton
    fun provideGetPopularMovies(movieRepository: MovieRepository, @ApplicationContext context: Context): GetPopularMovies {
        val token = context.getString(R.string.token)
        return GetPopularMovies(movieRepository, token)
    }

    @Provides
    @Singleton
    fun provideMovieRepository(dataSource: IMovieRemoteDataSource) : MovieRepository {
        return MovieRepository(dataSource)
    }

    @Provides
    @Singleton
    fun provideMovieRemoteDataSource(retrofit: RetrofitBuilder ): IMovieRemoteDataSource {
        return MovieRemoteDataSource(retrofit)
    }

    @Provides
    @Singleton
    fun provideDoLogin(loginRepository: LoginRepository): DoLogin {
        return DoLogin(loginRepository)
    }

    @Provides
    @Singleton
    fun provideLoginRepository( loginDataSource: ILoginDataStore): LoginRepository {
        return LoginRepository(loginDataSource)
    }

    @Provides
    @Singleton
    fun provideLoginDataSource( @ApplicationContext context: Context ): ILoginDataStore {
        return LoginDataSource(context = context)
    }

    @Provides
    @Singleton
    fun provideGetEmailKey(loginRepository: LoginRepository): GetEmailKey {
        return GetEmailKey(loginRepository)
    }

    @Provides
    @Singleton
    fun provideObtainToken( pushNotificationRepository: PushNotificationRepository): ObtainToken {
        return ObtainToken(pushNotificationRepository)
    }

    @Provides
    @Singleton
    fun providePushNotificationRepository( pushDataSource: IPushDataSource): PushNotificationRepository {
        return PushNotificationRepository(pushDataSource)
    }

    @Provides
    @Singleton
    fun provideIPushDataSource(): IPushDataSource {
        return FirebaseNotificationDataSource()
    }

//    Simulacro
@Provides
@Singleton
fun provideFinanzasLocalDataSource(@ApplicationContext context: Context): IFinanzasLocalDataSource {
    return FinanzasLocalDataSource(context)
}

    @Provides
    @Singleton
    fun finanzasRepository(localDataSource: IFinanzasLocalDataSource): FinanzasRepository {
        return FinanzasRepository(localDataSource)
    }

    @Provides
    @Singleton
    fun provideDeleteFinanza(repository: FinanzasRepository): DeleteFinanza {
        return DeleteFinanza(repository)
    }
    @Provides
    @Singleton
    fun provideDoEgreso(repository: FinanzasRepository): DoEgreso {
        return DoEgreso(repository)
    }
    @Provides
    @Singleton
    fun provideDoIngreso(repository: FinanzasRepository): DoIngreso {
        return DoIngreso(repository)
    }
    @Provides
    @Singleton
    fun provideGetRegistros(repository: FinanzasRepository): GetRegistros {
        return GetRegistros(repository)
    }
    @Provides
    @Singleton
    fun provideGetTotal(repository: FinanzasRepository): GetTotal {
        return GetTotal(repository)
    }

    //Books

    @Provides
    @Singleton
    fun provideFindBooks(bookRepository: BookRepository, @ApplicationContext context: Context): FindBooks {
        return FindBooks(bookRepository)
    }
    @Provides
    @Singleton
    fun provideSaveBook(bookRepository: BookRepository, @ApplicationContext context: Context): SaveBook {
        return SaveBook(bookRepository)
    }

    @Provides
    @Singleton
    fun bookRemoteDataSource(retrofiService: RetrofitBuilder): IBookRemoteDataSource {
        return BookRemoteDataSource(retrofiService)
    }

    @Provides
    @Singleton
    fun provideBookLocalDataSource(@ApplicationContext context: Context): IBookLocalDataSource {
        return BookLocalDataSource(context)
    }

    @Provides
    @Singleton
    fun bookRepository(remoteDataSource: IBookRemoteDataSource, localDataSource: IBookLocalDataSource): BookRepository {
        return BookRepository(remoteDataSource, localDataSource)
    }

}