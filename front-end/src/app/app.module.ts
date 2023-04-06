import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './components/header/header.component';
import { ImagenComponent } from './components/imagen/imagen.component';
import { BannerComponent } from './components/banner/banner.component';
import { HrComponent } from './components/utilities/hr/hr.component';
import { AboutMeComponent } from './components/about-me/about-me.component';
import { ExperienceComponent } from './components/experience/experience.component';
import { ProjectsComponent } from './components/projects/projects.component';
import { SkillsComponent } from './components/skills/skills.component';
import { ContactMeComponent } from './components/contact-me/contact-me.component';
import { FooterComponent } from './components/footer/footer.component';

//import { StorageService } from './services/storage.service';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    ImagenComponent,
    BannerComponent,
    HrComponent,
    AboutMeComponent,
    ExperienceComponent,
    ProjectsComponent,
    SkillsComponent,
    ContactMeComponent,
    FooterComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
