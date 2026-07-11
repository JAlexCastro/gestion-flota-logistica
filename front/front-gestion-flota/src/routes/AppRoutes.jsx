import { BrowserRouter, Routes, Route } from "react-router-dom";

import DashboardLayout from "../layouts/DashboardLayout";
import VehiculosPage from "../pages/Vehiculos/VehiculosPage";
import ConductoresPage from "../pages/Conductores/ConductoresPage";
import UsuariosPage from "../pages/Usuarios/UsuariosPage";
import MantencionesPage from "../pages/Mantenciones/MantencionesPage";
import RevisionesPage from "../pages/Revisiones/RevisionesPage";
///import SoapPage from "../pages/SOAP/SoapPage";
import FallasPage from "../pages/Fallas/FallasPage.jsx";
import DashboardPage from "../pages/Dashboard/DashboardPage";

import LoginPage from "../pages/Login/LoginPage";
import PrivateRoute from "./PrivateRoute";


function AppRoutes() {

    return (

        <BrowserRouter>

            <Routes>

                {/* Ruta pública */}

                <Route
                    path="/login"
                    element={<LoginPage />}
                />

                {/* Rutas protegidas */}

                <Route element={<PrivateRoute />}>

                    <Route element={<DashboardLayout />}>

                        <Route
                            path="/"
                            element={<DashboardPage />}
                        />

                        <Route
                            path="/vehiculos"
                            element={<VehiculosPage />}
                        />

                        <Route
                            path="/conductores"
                            element={<ConductoresPage />}
                        />

                        <Route
                            path="/usuarios"
                            element={<UsuariosPage />}
                        />

                        <Route
                            path="/mantenciones"
                            element={<MantencionesPage />}
                        />

                        <Route
                            path="/revisiones"
                            element={<RevisionesPage />}
                        />

                        <Route
                            path="/fallas"
                            element={<FallasPage />}
                        />

                    </Route>

                </Route>

            </Routes>

        </BrowserRouter>

    );

}

export default AppRoutes;