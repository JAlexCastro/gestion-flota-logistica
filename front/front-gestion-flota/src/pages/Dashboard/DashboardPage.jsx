import { useEffect, useState } from "react";
import DashboardCard from "./DashboardCard";
import "./Dashboard.css";

import { listarVehiculos } from "../../services/vehiculoService";
import { listarConductores } from "../../services/conductoresService";
import { listarMantenciones } from "../../services/mantencionService";
import { listarFallas } from "../../services/fallaService";
import { listarSoap } from "../../services/soapService";

function DashboardPage() {

    const [vehiculos, setVehiculos] = useState([]);
    const [conductores, setConductores] = useState([]);
    const [mantenciones, setMantenciones] = useState([]);
    const [fallas, setFallas] = useState([]);
    const [soaps, setSoaps] = useState([]);

    useEffect(() => {
        cargarDashboard();
    }, []);

    const cargarDashboard = async () => {

        const vehiculosData = await listarVehiculos();
        const conductoresData = await listarConductores();
        const mantencionesData = await listarMantenciones();
        const fallasData = await listarFallas();
        const soapsData = await listarSoap();

        setVehiculos(vehiculosData.data);
        setConductores(conductoresData.data);
        setMantenciones(mantencionesData.data);
        setFallas(fallasData.data);
        setSoaps(soapsData.data);

    };

    const fallasPendientes = fallas.filter(
        f => f.estado === "Pendiente"
    ).length;

    const fallasCriticas = fallas.filter(
        f => f.prioridad === "Crítica"
    ).length;

    return (

        <div className="dashboard">

            <h1>Dashboard Operacional</h1>

            <div className="dashboard-grid">

                <DashboardCard
                    title="Vehículos"
                    value={vehiculos.length}
                    icon="🚛"
                />

                <DashboardCard
                    title="Conductores"
                    value={conductores.length}
                    icon="👨‍✈️"
                />

                <DashboardCard
                    title="Mantenciones"
                    value={mantenciones.length}
                    icon="🔧"
                />

                <DashboardCard
                    title="Fallas Pendientes"
                    value={fallasPendientes}
                    icon="⚠️"
                />

                <DashboardCard
                    title="Fallas Críticas"
                    value={fallasCriticas}
                    icon="🚨"
                />

                <DashboardCard
                    title="SOAP Registrados"
                    value={soaps.length}
                    icon="🛡️"
                />

            </div>

        </div>

    );

}

export default DashboardPage;