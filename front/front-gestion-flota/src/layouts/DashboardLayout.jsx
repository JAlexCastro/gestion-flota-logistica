import { Outlet } from "react-router-dom";

import Navbar from "../components/Navbar/Navbar";
import Sidebar from "../components/Sidebar/Sidebar";

import "./DashboardLayout.css";

function DashboardLayout() {

    return (
        <div className="layout">

            <Navbar />

            <div className="layout-body">

                <Sidebar />

                <main className="content">
                    <Outlet />
                </main>

            </div>

        </div>
    );
}

export default DashboardLayout;